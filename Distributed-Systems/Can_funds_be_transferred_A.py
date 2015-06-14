class Tree():
    def __init__(self):
        self.nodes = {}
        self.depths = {1:0}

    def __repr__(self):
        return str(self.nodes)

    def __getitem__(self,child):
        return self.nodes[child]

    def insert(self,parent,child):
        self.nodes[child] = parent
        self.depths[child] = self.depths[parent] + 1

    def distance(self, nodeA, nodeB):
        if (nodeA == nodeB): return 1
        dist = 1
        while nodeA != nodeB:
            da = self.depths[nodeA]
            db = self.depths[nodeB]
            if (da == db):
                nodeA, nodeB = tree[nodeA], tree[nodeB]
                dist += 2
            elif (da < db):
                nodeA, nodeB = nodeA, tree[nodeB]
                dist += 1
            elif (da > db):
                nodeA, nodeB = tree[nodeA], nodeB
                dist += 1
        return dist

tree = Tree()

def init_server():
    print "Reading training set"
    for line in open("training.txt"):
        if "," in line:
            parent, child = map(int, line.strip().split(","))
            tree.insert(parent, child)
    sys.stdout.flush()

def process_client_connection(connection):
    while 1:
        message = read_string_from_socket(connection)
        if message == "END": break
        a, b, q = map(int, message.split(","))
        numNodes = tree.distance(a, b)
        if numNodes <= q: answer = "YES"
        else: answer = "NO"
        write_string_to_socket(connection, answer)
    connection.close()
