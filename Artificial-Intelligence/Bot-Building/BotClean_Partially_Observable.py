import random
import os.path

class Node:
    def __init__(self, x=0, y=0):
        self.x = x
        self.y = y

    def dist_to(self, target):
        return abs(target.x - self.x) + abs(target.y - self.y)

def next_move(posx, posy, board):
    n = len(board)
    cleared_map = [([0] * n) for i in range(n)]
    state_fname = "bot_clean_partially_observable.state"
    if os.path.exists(state_fname):
        state_f = open(state_fname)
        for y, line in enumerate(state_f):
            for x, state_char in enumerate(line):
                if x >= n:
                    continue
                if state_char == "-":
                    cleared_map[x][y] = 1
                elif state_char == "d":
                    cleared_map[x][y] = 2
    bot_node = Node(posy, posx)
    dirty_nodes = []
    fogged_nodes = []
    on_the_dirty_node = False
    for i in range(n):
        for j in range(len(board[i])):
            node = Node(j, i)
            if board[i][j] == "d" or cleared_map[j][i] == 2:
                dirty_nodes.append(node)
                if node.dist_to(bot_node) == 0:
                    cleared_map[j][i] = 1
                    on_the_dirty_node = True
                else:
                    cleared_map[j][i] = 2
            elif board[i][j] == "o" and cleared_map[j][i] != 1:
                fogged_nodes.append(node)
            elif board[i][j] == "-":
                cleared_map[j][i] = 1
    state_f = open(state_fname, "w")
    lines = []
    for y in range(n):
        line = ""
        for x in range(len(cleared_map[y])):
            if cleared_map[x][y] == 1:
                line += "-"
            elif cleared_map[x][y] == 2:
                line += "d"
            else:
                line += "o"
        lines.append(line + "\n")
    state_f.writelines(lines)
    state_f.flush()
    state_f.close()
    if on_the_dirty_node:
        print "CLEAN"
        return
    nearest_dirty_node = None
    for node in dirty_nodes:
        if nearest_dirty_node is None or node.dist_to(bot_node) < nearest_dirty_node.dist_to(bot_node):
            nearest_dirty_node = node
    if nearest_dirty_node is None:
        move_to_fog(bot_node, fogged_nodes)
    else:
        move_to_node(nearest_dirty_node.x - bot_node.x, nearest_dirty_node.y - bot_node.y)
        return

def move_to_fog(bot_node, fogged_nodes):
    if len(fogged_nodes) == 0:
        return
    search_scope = 2
    move_direction = None
    while move_direction is None:
        weights = [0, 0, 0, 0]
        for node in fogged_nodes:
            delta_x = node.x - bot_node.x
            delta_y = node.y - bot_node.y
            if abs(delta_y) < 2:
                if delta_x == search_scope:
                    weights[1] += 1  # right
                elif delta_x == -search_scope:
                    weights[0] += 1  # left
            if abs(delta_x) < 2:
                if delta_y == search_scope:
                    weights[3] += 1  # down
                elif delta_y == -search_scope:
                    weights[2] += 1  # up
        if sum(weights) == 0:
            search_scope += 1
            continue
        idx = weights.index(max(weights))
        temp_directions = []
        for i in range(len(weights)):
            if weights[i] == weights[idx]:
                if i == 0:
                    temp_directions.append("LEFT")
                elif i == 1:
                    temp_directions.append("RIGHT")
                elif i == 2:
                    temp_directions.append("UP")
                elif i == 3:
                    temp_directions.append("DOWN")
        move_direction = random.choice(temp_directions)
    print move_direction

def move_to_node(delta_x, delta_y):
    if delta_x < 0 :
        print "LEFT"
    elif delta_x > 0 :
        print "RIGHT"
    elif delta_y < 0 :
        print "UP"
    elif delta_y > 0 :
        print "DOWN"

if __name__ == "__main__":
    pos = [int(i) for i in raw_input().strip().split()]
    board = [[j for j in raw_input().strip()] for i in range(5)]
    next_move(pos[0], pos[1], board)
