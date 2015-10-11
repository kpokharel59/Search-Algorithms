# Search-Algorithms
Various Search algorithms that uses different heuristics to find optimal path.

For SearchUSA:
1) The inputs should will be given through the command line.
   % java SearchUSA searchtype srccityname destcityname
2) The searchtype should be either astar, greedy, or uniform.
3) The spelling of srccityname and destcityname must be the as given in usroads.txt. Do NOT change the names from lower case to upper case.
4) Use as a heuristic the straight line distance between cities. The straight-line distance between cities is computed using decimal degrees 
   of latitude and longitude, which also given in the file. There is a complication in computing straight line distance from longitude and 
   latitude that arises because the earth is roughly a sphere, not a cylinder. heuristic.txt is another file with a header comment indicating
   how the heuristic should work.

For SearchRomania:
1) The inputs should will be given through the command line.
   % java SearchUSA searchtype srccityname destcityname
2) The searchtype should be either DFS or BFS.
3) The spelling of srccityname and destcityname must be the as given in roads.txt. Please DO NOT change the names from lower case to upper case.
 