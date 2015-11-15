n = gets().strip.to_i
m = gets().strip.to_i
arr = [(1..n + 2).map{|x| 0}]
(1..n).each do |row|
  arr += [[0] + gets().strip.split(" ").map{|x| x.to_i} + [0]]
end

arr += [(1..n + 2).map{|x| 0}]

def dfs(arr, r, c, currentCount)
  if arr[r][c] == 1
    arr[r][c] = currentCount
    (-1..1).each do |delR|
      (-1..1).each do |delC|
        if (delR * delR + delC * delC) > 0
          if arr[r + delR][c + delC] == 1
            currentCount = dfs(arr, r + delR, c + delC, currentCount + 1)
          end
        end
      end
    end
  end

  return currentCount
end

(1..n).each do |r|
  (1..m).each do |c|
    if arr[r][c] == 1
      dfs(arr, r, c, 2)
    end
  end
end

(1..n).each do |r|
end

puts arr.flatten.max - 1
