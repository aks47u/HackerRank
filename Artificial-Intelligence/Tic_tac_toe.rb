class GameState
  attr_accessor :current_player, :board, :moves, :rank, :player_figure, :current_move
  
  def initialize(current_player, board, player_figure = 'X')
    self.current_player = current_player
    self.board = board
    self.player_figure = player_figure
    self.moves = []
    self.current_move = -999
  end

  def final_state?
    winner || draw?
  end

  def draw?
    board.compact.size == 9 && winner.nil?
  end

  def rank
    @rank ||= final_state_rank || intermediate_state_rank
  end

  # this is only ever called when it's the AI's (the X player) turn
  def next_move

    moves.max_by {|x| x.rank }
  end

  def final_state_rank
    if final_state?
      return 0 if draw?
      winner == player_figure ? 1 : -1
    end
  end

  def intermediate_state_rank
    # recursion, baby
    ranks = moves.collect{ |game_state| game_state.rank }
    if current_player == player_figure
      ranks.max
    else
      ranks.min
    end
  end  

  def winner
    @winner ||= [
     # horizontal wins
     [0, 1, 2],
     [3, 4, 5],
     [6, 7, 8],

     # vertical wins
     [0, 3, 6],
     [1, 4, 7],
     [2, 5, 8],

     # diagonal wins
     [0, 4, 8],
     [6, 4, 2]
    ].collect { |positions|
      ( board[positions[0]] == board[positions[1]] &&
        board[positions[1]] == board[positions[2]] &&
        board[positions[0]] ) || nil
    }.compact.first
  end
end

class GameTree
  attr_accessor :player_figure
  def generate(current_player, board, player_figure = 'X')
    initial_game_state = GameState.new(current_player, board, player_figure)
    self.player_figure = player_figure
    generate_moves(initial_game_state)
    initial_game_state
  end

  def generate_moves(game_state)
    next_player = (game_state.current_player == 'X' ? 'O' : 'X')
    game_state.board.each_with_index do |player_at_position, position|
      unless player_at_position
        next_board = game_state.board.dup
        next_board[position] = game_state.current_player

        next_game_state = GameState.new(next_player, next_board, player_figure)
        next_game_state.current_move = position
        game_state.moves << next_game_state
        generate_moves(next_game_state)
      end
    end
  end
end
def opponent(player)
    return 'O' if player == 'X'
    
    'X'
end

player = gets.chomp
board = Array.new(3) { gets.scan(/\w/) }.flatten!
board.map! { |e| e=='_' ? nil : e }

if board.compact.first.nil?
  print '2 0' 
elsif board.compact.length == 2 && board[6] == player && board[4] == opponent(player)
  print '0 2'    
else
  step = GameTree.new.generate(player, board, player)
  print step.next_move.current_move.to_s(3).rjust(2, '0').scan(/\w/).join(' ')
end
