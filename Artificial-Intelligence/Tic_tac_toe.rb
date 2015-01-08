def get_move_num(board)
  move = 1;
  for i in 0..2
    for j in 0..2
      if(board[i][j] != '_')
        move = move + 1;
      end
    end
  end
  return move;
end

def can_complete(player,board)
  if(board[0][0] == player && board[1][0] == player && board[2][0] == '_')
    puts "2 0";
  elsif(board[0][0] == player && board[2][0] == player && board[1][0] == '_')
    puts "1 0";
  elsif(board[0][0] == player && board[0][1] == player && board[0][2] == '_')
    puts "0 2";
  elsif(board[0][0] == player && board[0][2] == player && board[0][1] == '_')
    puts "0 1";
  elsif(board[0][0] == player && board[1][1] == player && board[2][2] == '_')
    puts "2 2";
  elsif(board[0][0] == player && board[2][2] == player && board[1][1] == '_')
    puts "1 1";
  elsif(board[0][2] == player && board[1][2] == player && board[2][2] == '_')
    puts "2 2";
  elsif(board[0][2] == player && board[2][2] == player && board[1][2] == '_')
    puts "1 2";
  elsif(board[0][2] == player && board[0][1] == player && board[0][0] == '_')
    puts "0 0";
  elsif(board[0][2] == player && board[0][0] == player && board[0][1] == '_')
    puts "0 1";
  elsif(board[0][2] == player && board[1][1] == player && board[2][0] == '_')
    puts "2 0";
  elsif(board[0][2] == player && board[2][0] == player && board[1][1] == '_')
    puts "1 1";
  elsif(board[2][0] == player && board[1][0] == player && board[0][0] == '_')
    puts "0 0";
  elsif(board[2][0] == player && board[0][0] == player && board[1][0] == '_')
    puts "1 0";
  elsif(board[2][0] == player && board[2][1] == player && board[2][2] == '_')
    puts "2 2";
  elsif(board[2][0] == player && board[2][2] == player && board[2][1] == '_')
    puts "2 1";
  elsif(board[2][0] == player && board[1][1] == player && board[0][2] == '_')
    puts "0 2";
  elsif(board[2][0] == player && board[0][2] == player && board[1][1] == '_')
    puts "1 1";
  elsif(board[2][2] == player && board[2][1] == player && board[2][0] == '_')
    puts "2 0";
  elsif(board[2][2] == player && board[2][0] == player && board[2][1] == '_')
    puts "2 1";
  elsif(board[2][2] == player && board[1][2] == player && board[0][2] == '_')
    puts "0 2";
  elsif(board[2][2] == player && board[0][2] == player && board[1][2] == '_')
    puts "1 2";
  elsif(board[2][2] == player && board[1][1] == player && board[0][0] == '_')
    puts "0 0";
  elsif(board[2][2] == player && board[0][0] == player && board[1][1] == '_')
    puts "1 1";
  elsif(board[1][1] == player && board[0][0] == player && board[2][2] == '_')
    puts "2 2";
  elsif(board[1][1] == player && board[0][1] == player && board[2][1] == '_')
    puts "2 1";
  elsif(board[1][1] == player && board[0][2] == player && board[2][0] == '_')
    puts "2 0";
  elsif(board[1][1] == player && board[1][0] == player && board[1][2] == '_')
    puts "1 2";
  elsif(board[1][1] == player && board[1][2] == player && board[1][0] == '_')
    puts "1 0";
  elsif(board[1][1] == player && board[2][0] == player && board[0][2] == '_')
    puts "0 2";
  elsif(board[1][1] == player && board[2][1] == player && board[0][1] == '_')
    puts "0 1";
  elsif(board[1][1] == player && board[2][2] == player && board[0][0] == '_')
    puts "0 0";
  else
    return false;
  end
  return true;
end

def must_block(player,board)
  if player == 'X'
    return can_complete('O',board);
  else
    return can_complete('X',board);
  end
end

def next_move(player,board,move)
  completed = false;
  blocking = false;
  if(move == 1)
    puts "2 0";
  elsif(move == 2)
    if(board[1][1] == 'X')
      puts "2 2";
    else
      puts "1 1";
    end
  elsif(move == 3)
    if(board[0][1] == 'O' || board[1][0] == 'O' || board[1][2] == 'O' || board[2][1] == 'O')
      puts "1 1";
    elsif(board[1][1] == 'O')
      puts "0 2";
    elsif(board[0][2] == 'O' || board[2][2] == 'O')
      puts "0 0";
    else
      puts "2 2";
    end
  else
    completed = can_complete(player,board)
    if(completed == false)
      blocking = must_block(player,board);
      if(blocking == false)
        prevent = can_complete(player,board)
        if(move == 4 && (board[0][0] == 'X' || board[0][2] == 'X' || board[2][0] == 'X' || board[2][2] == 'X') && (board[0][1] == 'X' || board[1][0] == 'X' || board[1][2] == 'X' || board[2][1] == 'X'))
          if(board[0][1] == 'X' || board[2][1] == 'X')
            puts "1 0";
          else
            puts "0 1";
          end
        elsif(move == 4 && ((board[0][0] == 'X' && board[2][2] == 'X') || (board[0][2] == 'X' && board[2][0] == 'X') || (board[0][1] == 'X' && board[2][1] == 'X') || (board[1][0] == 'X' && board[1][2] == 'X')))
          if((board[0][0] == 'X' && board[2][2] == 'X') || (board[0][2] == 'X' && board[2][0] == 'X'))
            puts "0 1";
          else
            puts "0 0";
          end
        elsif(move == 6 && (board[2][1] == 'X' || board[0][1] == 'X') && (board[1][0] == 'X' || board[1][2] == 'X') && board[2][2] == '_')
          puts "2 2";
        elsif(board[0][0] == "_")
          puts "0 0";
        elsif(board[0][2] == "_")
          puts "0 2";
        elsif(board[2][2] == "_")
          puts "2 2";
        elsif(board[2][0] == "_")
          puts "2 0";
        elsif(board[0][1] == "_")
          puts "0 1";
        elsif(board[2][1] == "_")
          puts "2 1";
        elsif(board[1][0] == "_")
          puts "1 0";
        elsif(board[1][2] == "_")
          puts "1 2";
        else
          puts "1 1";
        end
      end
    end
  end
end

player = gets.chomp

board = Array.new(3) { gets.scan /\w/ }
move = get_move_num(board);
next_move(player,board,move);
