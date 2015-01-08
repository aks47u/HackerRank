def nextMove(player,board)
  k = rand(2);
  if player.eql?("h")
    if k == 0
      for i in 0..3
        for j in 0..7
          if board[2*i+1][j] == "-" && board[2*i+1][j+1] == "-"
            return 2*i+1,j
          end
        end
      end
      3.downto(0){|i|
        7.downto(0){|j|
          if board[2*i][j] == "-" && board[2*i][j+1] == "-"
            return 2*i,j
          end
        }
      }
    else
      3.downto(0){|i|
        7.downto(0){|j|
          if board[2*i][j] == "-" && board[2*i][j+1] == "-"
            return 2*i,j
          end
        }
      }
      for i in 0..3
        for j in 0..7
          if board[2*i+1][j] == "-" && board[2*i+1][j+1] == "-"
            return 2*i+1,j
          end
        end
      end
    end
    puts "errorH"
  elsif player.eql?("v")
    if k == 0
      for i in 0..6
        for j in 0..3
          if board[i][2*j+1] == "-" && board[i+1][2*j+1] == "-"
            return i,2*j+1
          end
        end
      end
      6.downto(0){|i|
        3.downto(0){|j|
          if board[i][2*j] == "-" && board[i+1][2*j] == "-"
            return i,2*j
          end
        }
      }
    else
      6.downto(0){|i|
        3.downto(0){|j|
          if board[i][2*j] == "-" && board[i+1][2*j] == "-"
            return i,2*j
          end
        }
      }
      for i in 0..6
        for j in 0..3
          if board[i][2*j+1] == "-" && board[i+1][2*j+1] == "-"
            return i,2*j+1
          end
        end
      end
    end
    puts "errorV"
  else
    puts "error"
  end
end

player = gets.strip!()

board = Array.new(8)

(0...8).each do |i|
  board[i] = gets
end
a,b = nextMove(player,board)
puts a.to_s+" "+b.to_s
