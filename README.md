# Maze Upgrade Game

## Table Of Contents

* [About](#about)
* [Current Features](#current-features)
* [Upcoming Features](#upcoming-features)
* [Editing or Adding Themes](#theme-customization)
    * [Editing Themes](#to-edit-a-theme)
    * [Adding Themes](#to-create-a-new-theme)
* [Known Bugs](#known-bugs)
* [Quick Start](#quick-start)
    * [Easy Build](#easy-option)
    * [Involved Build](#controlled-option)
* [How To Play](#how-to-play)
* [Implementation](#implementation)

---

## About

This is a maze upgrade game that is full of procedurally generated mazes.  
If you'd like to run it right away and read through this later, see <a href="#quick-start">Quick Start</a>.

## Current Features

* A maze, generated with [depth first search](https://en.wikipedia.org/wiki/Depth-first-search#Applications)
* Player movement, restricted by walls.
* Path generation with space, using [Greedy DFS](https://www.geeksforgeeks.org/dsa/greedy-best-first-search-algorithm/)
* Theme selection with the arrow keys as well as with the settings icon
* Full screen display
* Menu items, Settings and Help
* Optional very customizable themes, any amount. [Theme Editing](#theme-customization)

## Upcoming Features

* Upgrade System, allowing users to pick upgrades as they go.
* Maze regeneration on maze completion
* Sounds:
    * Moving
    * Completion
    * Powerup Grabbed
    * Powerup Used
    * And more that I cannot remember!!
* Possibly Enemies
* A timer that the user has to race
* The maze should grow in complexity over completions
* More settings, including post processing effects, and sound themes.

---

## Theme Customization

### To edit a theme

**(Note: This currently only works when running from the command line in this release.)**
* Navigate to `resources/themes/themename.png` and open that theme in your paint editor of choice.
* Decide which color you want to change, the order is:
    * Wall
    * Target
    * Background
    * Visited
    * Start
    * End
    * Path
    * Player
    * Debug   
      It is recommended to keep Background and Visited the same color, otherwise the visualization will flicker at the start of maze generation.
* Save the file, and either relaunch the game or just move your player, and you should see the theme update!

### To create a new theme

* Navigate to `resources/themes/themetocopy.png`
* Duplicate that file, and name it something different (ocean.png)
* Open the duplicated file, and change the colors as you choose.
* The next time you open settings, the theme should be automagically added and selectable!

## Known Bugs

List of known bugs as of January 12th, 2026.

* Player disappearing on path draw
* Path Generation breaking when player is standing on the end node.  
* Jar file only running when it can see `resources/`
* None more! Let me know if you see anything :)

---

## Quick Start

### Easy Option

Download `MazeGenGame-0.3.0-MILESTONE.jar` and the `resources/` folder from releases (packaged as a .zip) 
Double click the MazeGenGame-0.3.0-MILESTONE.jar file. Enjoy!  
The jar will **ONLY** run if resources is in the same folder. Working on a fix. As we speak.

### Controlled Option

Download the `src/` and `resources/` folders. Place them in the same directory. We'll call that `root/`. From the root, run these commands from your terminal.

Powershell:
```pwsh
New-Item out/ #create the out directory.  
javac -d out src/core/Main.java #compile the java files.  
java -cp out core.Main #run the game
```

Terminal:

```cmd
mkdir -p out #make the directory  
javac -d out src/core/Main.java #compile the java files.  
java -cp out core.Main #run the game!!
```

See [How to Play](#how-to-play) for instructions.

---

## How to Play

* Use WASD or the arrow keys to move. You cannot, (or at least shouldn't be able to) move through walls.
* If you're stuck, use space to generate the fastest path to the end.
* Use numbers 1-5 to pick your theme! (You can also do this in settings)
* Q to quit
* F or Escape to toggle fullscreen. 
* That's it for now! Please enjoy my game :)

## Implementation

* I may have gone a bit overboard on the amount of files, consisting of 6 different packages, with a few containing inner modules.
* As mentioned earlier, the maze generation was done with a depth first search approach, searching forwards until there are no more cells around it can visit and then going backwards until the program sees a spot it hasn't been to before. This took a lot less time than I thought it would, and is pretty efficient, even for large mazes.
* The pathfinding was done with a greedy best first algorithm, which I picked because it was the fastest to code while being the most visually pleasing as it generates.
* Swing is generally very ugly, so most of my time on this project was spent making it.. not ugly. The UI package itself is the biggest package, containing overrides for buttons and drop downs currently, but more will be added as development ensues.
* The themes are probably my favourite implementation here. I stored the theme files as a png, allowing me to easily edit them as well as providing some nice friendly customization if users feel like digging in.
