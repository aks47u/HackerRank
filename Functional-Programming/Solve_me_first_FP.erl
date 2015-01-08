-module(solution).
-export([main/0]).

solveMeFirst(A, B) ->
    A + B.

main() -> 
    {ok, [A, B]} = io:fread("", "~d~d"),
    Res = solveMeFirst(A,B),
    io:format("~p~n",[Res]).
