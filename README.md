
# TUMazing Maze Runner

## About

### Base Code Structure
The most important part of the code, is the `Maze`, followed by the `Position` class. The `Maze` class is responsible for reading all worlds from the `Maps` folder.
After a level is chosen, the `Maze` class reads the whole file, loading the different positions of each object type into multiple lists, and drawing the textures of the object onto the screen. One of the most important methods is the boolean `isWall(Position)`. This method is used during moving to make sure, that no entity can walk into the walls. Doors are also treated as walls, until the player has collected the key.
The `Position` class functions as a datatype and is used to store and compare different positions. We differentiate between the actual coordinates given through the level file, and the actual coordinates, which are different, as the "blocks" have a resolution of 16x16 pixels. The class includes different methods to calculate between these two coordinate systems.

### Game mechanics
All moving objects are considered to be entities in the world. The abstract `Entity` superclass implements lives and basic functionality (eg. moving) for the entities. Moving uses checking methods from the `Maze` class and offsets certain values, to make up for the character being larger than a single pixel.
The `Character` class inherits from the `Entity` superclass and adds the missing functionality (eg. key collection, exiting the maze, etc.).

> Traps are missing here

The `Mob` class also inherits from the `Entity` superclass and implements the mobs behavior.

During the game, the user can see a Heads Up Display (HUD). This is handled in the `HUD` class. It implements methods to remove hearts from the screen, as the player loses lives, and can toggle the status of key collection.

#### Map loading
The `Maze` class defines the Maze in the game and is also responsible for loading and displaying the Map.
When a new map is created, the `getProperties(String levelname)` method loads all property values (Height, Width) from the given file. Then we iterate through the file and fill an `int[][]` array, which represents the map with the appropriate values. This array stores values in the format of `arr[x][y]=objectType`. By doing so, we were able to make the Game more memory efficient, as we do not need a single object for each wall, etc.
#### Map Generation
We have implemented random map generation as an additional feature into the game. All of this is handled in the `MapGenerator` class.
First we initialize the Map array. We begin by filling out the outer walls, then we place the inner walls. To to this, we iterate through every row of the map, and fill 1/3 of the blocks with walls. Now we spawn the mobs, by again iterating through the rows and placing mobs with a 1/15 ratio, if there is no wall at that position. We also place traps with the same 1/15 ratio, where there is no wall and no mob. As a final step, we then place a key at some random point in the map, while checking that there is no other object at the chosen location.

After we generate a random map. We use the `AStar` class (which implements the A* searching and path-finding algorithm) to check if there is a path between the entry position, the key, and the exit. If there is a path, the generated map is playable. We then create a new `GameScreen` with the random map.



### Utility classes



## Code structure

```BASH
└── de
    └── tum
        └── cit
            └── ase
                └── maze
                    ├── entitity
                    │   ├── Character.java
                    │   ├── Entity.java
                    │   └── Mob.java
                    ├── exception
                    │   └── MapFormatWrongException.java
                    ├── GameScreen.java
                    ├── hud
                    │   └── HUD.java
                    ├── map
                    │   ├── Key.java
                    │   └── Maze.java
                    ├── MazeRunnerGame.java
                    ├── menu
                    │   ├── EndScreen.java
                    │   ├── LevelSelectScreen.java
                    │   ├── MenuScreen.java
                    │   └── PauseScreen.java
                    ├── pathfind
                    │   ├── AStar.java
                    │   ├── Grid.java
                    │   └── Node.java
                    └── util
                        ├── Direction.java
                        ├── GameState.java
                        ├── MapGenerator.java
                        ├── Position.java
                        └── TextureUtil.java

```
#### Class explanation
- `Character.java`: This class inherits from the Entity superclass and adds functionality specific to the player character, such as key collection and exiting the maze.  
- `Entity.java`: This abstract superclass implements lives and basic functionality (e.g., moving) for all entities in the game.  
- `Mob.java`: This class also inherits from the Entity superclass and implements the behavior of the mobs (enemies) in the game.  
- `MapFormatWrongException.java`: This class handles exceptions related to incorrect map formatting.  
- `GameScreen.java`: This class is responsible for rendering the game screen.  
- `HUD.java`: This class handles the Heads Up Display (HUD) that shows the player's lives and key collection status.  
- `Key.java`: This class represents the key that the player must collect to exit the maze.  
- `Maze.java`: This class defines the maze in the game and is responsible for loading and displaying the map.  
- `MazeRunnerGame.java`: This is the main class that runs the game.  
- `EndScreen.java`, `LevelSelectScreen.java`, `MenuScreen.java`, `PauseScreen.java`: These classes handle different screens in the game menu.  
- `AStar.java`, `Grid.java`, `Node.java`: These classes implement the A* search and path-finding algorithm used in the game.  
- `Direction.java`, `GameState.java`, `MapGenerator.java`, `Position.java`, `TextureUtil.java`: These utility classes provide various functionalities such as direction handling, game state management, map generation, position handling, and texture utilities.

## How to play the game

### Main Goal
The main goal of the game, is to find and collect a key, and leave through one of the possible exits of the maze. You need to be careful not to lose all lives by running into traps or enemies.

### Level Selection
By default, there are 4 different levels, which you can choose through the game's menu. We have additionally implemented a random map generator.


