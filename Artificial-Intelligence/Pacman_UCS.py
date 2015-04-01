import collections
import heapq

class PriorityQueue:
    def __init__(self):
        self.elements = []
    
    def is_empty(self):
        return len(self.elements) == 0
    
    def put(self, item, priority):
        heapq.heappush(self.elements, (priority, item))
    
    def get(self):
        return heapq.heappop(self.elements)[1]
    
    def contains(self, item):
        return not self.is_empty() and item in zip(*self.elements)[1]
    
def ucs(r, c, pacman_r, pacman_c, food_r, food_c, grid):
    result = ucs_iterative(r, c, pacman_r, pacman_c, food_r, food_c, grid)
    path = reconstruct_path((food_r, food_c), result)
    print len(path)-1
    for pos in path:
        print str(pos[0]) + ' ' + str(pos[1])

def ucs_iterative(r, c, pacman_r, pacman_c, food_r, food_c, grid):
    fringe = PriorityQueue()
    came_from = {}
    cost_so_far = {}
    start = (pacman_r, pacman_c)
    fringe.put(start, 0)
    cost_so_far[start] = 0
    came_from[start] = None

    while not fringe.is_empty() > 0:
        current = fringe.get()

        if is_goal(current, grid):
            return came_from

        neighbours = get_neighbours(current, grid)
        for next in neighbours:
            new_cost = cost_so_far[current] + cost_of_move(next, grid)

            if (next not in came_from and not fringe.contains(next)) or (new_cost < cost_so_far[next]):
                came_from[next] = current
                cost_so_far[next] = new_cost
                fringe.put(next, new_cost)

def cost_of_move(pos, grid):
    return 0 if is_goal(pos, grid) else 1

def get_neighbours(pos, grid):
    (x, y) = pos
    neighbours = [(x-1, y  ),
                  (x,   y-1),
                  (x,   y+1),
                  (x+1, y  )]
    neighbours = filter(lambda x: is_passable(x, grid), neighbours)
    return neighbours

def is_passable(pos, grid):
    return grid[pos[0]][pos[1]] != '%'

def is_goal(pos, grid):
    return grid[pos[0]][pos[1]] == '.'

def is_start(pos, grid):
    return grid[pos[0]][pos[1]] == 'P'

def reconstruct_path(goal, came_from):
    current = goal
    path = [current]
    while not is_start(current, grid):
        current = came_from[current]
        path.append(current)
    return path[::-1]

pacman_r, pacman_c = [ int(i) for i in raw_input().strip().split() ]
food_r, food_c = [ int(i) for i in raw_input().strip().split() ]
r,c = [ int(i) for i in raw_input().strip().split() ]

grid = []
for i in xrange(0, r):
    grid.append(raw_input().strip())

ucs(r, c, pacman_r, pacman_c, food_r, food_c, grid)
