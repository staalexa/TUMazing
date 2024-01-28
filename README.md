
# TUMazing Maze Runner

## About

### Base Code Structure
The core of the code is structured around several classes, primarily the `Maze` and `Position` classes.
- `Maze`: This class is responsible for reading all worlds from the `Maps` folder. It loads the different positions of each object type into multiple lists and draws the textures of the object onto the screen. It also includes a `boolean isWall(Position)` method used during moving to ensure that no entity can walk into the walls.
- `Position`: This class functions as a datatype and is used to store and compare different positions. It includes different methods to calculate between two coordinate systems.

### Game mechanics
All moving objects are considered to be entities in the world. The abstract `Entity` superclass implements lives and basic functionality (eg. moving) for the entities. Moving uses checking methods from the `Maze` class and offsets certain values, to make up for the character being larger than a single pixel.
The `Character` class inherits from the `Entity` superclass and adds the missing functionality (eg. key collection, exiting the maze, etc.).

Here is a more detailed description of the classes:
- `Entity`: This abstract superclass implements lives and basic functionality (e.g., moving) for all entities in the game.
- `Character`: This class inherits from the `Entity` superclass and adds functionality specific to the player character, such as key collection and exiting the maze.
- `Mob`: This class also inherits from the `Entity` superclass and implements the behavior of the mobs (enemies) in the game.

> Traps are missing here

#### HUD
During the game, the user can see a Heads Up Display (HUD). This is handled in the `HUD` class. It implements methods to remove hearts from the screen, as the player loses lives, and can toggle the status of key collection.

#### Map loading
The `Maze` class defines the Maze in the game and is also responsible for loading and displaying the Map.
When a new map is created, the `getProperties(String levelname)` method loads all property values (Height, Width) from the given file. Then we iterate through the file and fill an `int[][]` array, which represents the map with the appropriate values. This array stores values in the format of `arr[x][y]=objectType`. By doing so, we were able to make the Game more memory efficient, as we do not need a single object for each wall, etc.
#### Map Generation
We have implemented random map generation as an additional feature into the game. All of this is handled in the `MapGenerator` class.
First we initialize the Map array. We begin by filling out the outer walls, then we place the inner walls. To to this, we iterate through every row of the map, and fill 1/3 of the blocks with walls. Now we spawn the mobs, by again iterating through the rows and placing mobs with a 1/15 ratio, if there is no wall at that position. We also place traps with the same 1/15 ratio, where there is no wall and no mob. As a final step, we then place a key at some random point in the map, while checking that there is no other object at the chosen location.

After we generate a random map. We use the `AStar` class (which implements the A* searching and path-finding algorithm) to check if there is a path between the entry position, the key, and the exit. If there is a path, the generated map is playable. We then create a new `GameScreen` with the random map.


#### Utility Classes
Several utility classes provide various functionalities:  
Direction: Handles direction.
- `GameState`: Manages game state.
- `MapGenerator`: Handles map generation.
- `Position`: Handles position.
- `TextureUtil`: Provides texture utilities.

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
#### Detailed class explanation
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

# How to play the game

The main goal of the game is to find and collect a key, and leave through one of the possible exits of the maze. You need to be careful not to lose all lives by running into traps or enemies. By default, there are 4 different levels, which you can choose through the game's menu. We have additionally implemented a random map generator.

### Custom Maps
The game will accept custom map files from the user. To load a custom map into the game, place the appropriate `.properties` file into the `maps` folder of the game.
Every Map needs to have at least the following to work (otherwise, the map will not load)
- Height/Width values expressed as `Height=x` and `Width=y` (x and y can be any positive integer). These values determine the size of the map.
- The different objects within the map. They need to adhere to the format `x,y=z` (x and y can be any positive integer, representing x and y coordinates of the given object in the map; z needs to adhere to the object list below)

| **Value** |  **Type**   |
|:---------:|:-----------:|
|     0     |    Wall     |
|     1     | Entry point |
|     2     |    Exit     |
|     3     |    Fire     |
|     4     |    Enemy    |
|     5     |     Key     |

- Please also consider the following **limitations**:
    1. Every map needs to at least have one key, one entry and one exit.
    2. There can only be one Entry

### Controls
- `WASD` or `Arrow Keys` to move
- `ESC` to pause the game

## Credits
- Logo: Technical University of Munich
- Textures: provided by Technical University of Munich
- Sound: provided by [openGameArt](https://opengameart.org/)
- LibGDX: [libGDX](https://libgdx.badlogicgames.com/)
