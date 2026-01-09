# Maze Upgrade Game

## About

This is a maze upgrade game that is full of procedurally generated mazes.  
If you'd like to run it right away and read through this later, see <a href="#quick-start">Quick Start</a>.
The game will go on to include powerups, sounds, and post-procesing. See <a href="#to-be-added">To Be Added </a> 
To see what you can do right now, see <a href="#current-features">Current Features</a>

---

## Current Features

<ul>
  <li>A maze, generated with <a href="https://en.wikipedia.org/wiki/Depth-first_search#Applications">depth first search</a></li>
  <li>Player movement, restricted by walls.</li>
  <li>Path generation with space, using <a href="https://www.geeksforgeeks.org/dsa/greedy-best-first-search-algorithm/">Greedy DFS</a></li>
  <li>Theme selection with the arrow keys as well as with the settings icon</li>
  <li>Full screen display</li>
  <li>Menu items, Settings and Help</li>
  <li>If you REALLY want to dig in, you can go into 
    <code>resources/themes/whatever.png</code>, and create or edit the current themes to customize your experience. The drop down menu will update with your new theme, and the colors should work automatically as long as the bars are sixteen pixels wide. </li>
</ul>


## To Be Added

<ul>
  <li>Upgrade System, allowing users to pick upgrades as they go. </li>
  <li>Maze regeneration on maze completion</li>
  <li>Sounds:
    <ul>
      <li> Moving </li>
      <li> Completion</li>
      <li> Powerup Grabbed </li>
      <li> Powerup Used</li>
      <li> And more that I cannot remember!!</li>
    </ul>
  </li>
  <li>Possibly Enemies</li>
  <li>A timer that the user has to race</li>
  <li>The maze should grow in complexity over completions</li>
  <li>More settings, including post processing effects, and sound themes. </li>
</ul>

---

## Known Bugs

<ul>
  <li>Player disappearing on path draw</li>
  <li>None more! Let me know if you see anything :)</li>
</ul>

---

## Quick Start

### Easy Option

Double click the {}.jar file. Enjoy!

### Controlled option

Ensure you are in the root directory of the project.  
You can choose where output files compile to by changing the "out" in the commands (ie. `build`)

Powershell:  
`javac -d out src/core/Main.java; java -cp out core.Main`  

Terminal:  
`javac -d out src/core/Main.java && java -cp out core.Main`   

See <a href="#how-to-play">How to play</a> for instructions. 

---

## How to Play

<ul>
  <li>Use WASD or the arrow keys to move. You cannot. (or at least shouldn't be able to) move through walls. </li>
  <li>If you're stuck, use space to generate the fastest path to the end. </li>
  <li>Use numbers 1-5 to pick your theme! (You can also do this in settings) </li>
  <li>That's it for now! Please enjoy my game :)</li>  
</ul>

## Implementation

I may have gone a bit overboard on the amount of files, consisting of 6 different packages, with a few containing inner modules.  
As mentioned earlier, the maze generation was done with a depth first search approach, searching forwards until there are no more cells around it can visit and then going backwards until the program sees a spot it hasn't been to before. This took a lot less time than I thought it would, and is pretty efficient, even for large mazes.    
The pathfinding was done with a greedy-bfs algorithm, which I picked because it was the fastest to code while being the most visually pleasing as it generates.   
Swing is generally very ugly, so most of my time on this project was spent making it.. not ugly. The UI package itself is the biggest package, containing overrides for buttons and drop downs currently, but more will be added as development ensues.   
The themes are probably my favourite implementation here. I stored the theme files as a png, allowing me to easily edit them as well as providing some nice user friendly customization if they so choose. 
