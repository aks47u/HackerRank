import socket, struct
from collections import defaultdict

def getOutput(dataSource, inputData):
    output = []
    if inputData[1] == 1:
        size, command, setID, key, score = inputData
        dataSource[setID][key] = score
        output.append(0)
    elif inputData[1] == 2:
        size, command, setID, key = inputData
        if setID in dataSource and key in dataSource[setID]:
            del dataSource[setID][key]
        output.append(0)
    elif inputData[1] == 3:
        size, command, setID = inputData
        output.append(1)
        output.append(len(dataSource[setID])) 
    elif inputData[1] == 4:
        size, command, setID, key = inputData
        output.append(1)
        if setID in dataSource and key in dataSource[setID]:
            output.append(dataSource[setID][key])
        else:
            output.append(0)
    elif inputData[1] == 5:
        lo, hi = inputData[-2:]
        setS = inputData[2:2 + (len(inputData) - 5)]
        valid = []
        for setID in setS:
            if setID in dataSource:
                for key in dataSource[setID]:
                    score = dataSource[setID][key]
                    if lo <= score <= hi:
                        valid.append((key, score))
        K = 2 * len(valid)
        output.append(K)
        valid.sort()
        for key, score in valid:
            output.append(key)
            output.append(score)
    return output

def go(conn):
    dataSource = defaultdict(dict)
    while 1:
        inputData = []
        numIntsToRead = struct.unpack('!L', conn.recv(4))[0]
        inputData.append(numIntsToRead)
        for i in xrange(numIntsToRead):
            inputData.append(struct.unpack('!L', conn.recv(4))[0])
        if inputData[1] == 6: break
        for item in getOutput(dataSource, inputData):
            conn.send(struct.pack('!L', item))

SERVER_SOCKET_PATH = "./socket"
MAX_CONNECTIONS = 8
server = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
server.bind(SERVER_SOCKET_PATH)
server.listen(MAX_CONNECTIONS)
while 1:
    conn, addr = server.accept()
    go(conn)
