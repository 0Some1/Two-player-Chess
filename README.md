# Two-player-Chess
Two-player Chess

## Game Description

In this project, we will implement a two-player chess game that includes offline and online modes.

### Chess Rules

Chess is a two-player turn-based game where the starting board always has the pieces face up, and white makes the first move. The objective for both players is to capture the opponent's king.

The game consists of 6 types of pieces:

1. Pawn: Moves forward one space on each move, except for the first move where it can move two spaces forward. Captures opponent's pieces diagonally.
2. Rook: Moves any number of spaces horizontally, vertically, or in a straight line. Captures opponent's pieces by taking their place on the board.
3. Knight: Moves in an L-shape, either two spaces horizontally and one space vertically or one space horizontally and two spaces vertically. Captures opponent's pieces at its destination.
4. Bishop: Moves any number of spaces diagonally in any direction. Captures opponent's pieces along its path. The bishop's movement is limited to squares of the same color throughout the game.
5. Queen: Moves to any square in all directions—up, down, right, left, and diagonally. It combines the movement of the rook and bishop.
6. King: Moves to any of the 8 adjacent squares. Captures opponent's pieces at these locations. The game ends when the king is captured.

### Additional Game Mechanics

- Piece Capture: Two pieces cannot occupy the same square. The later arrival removes the piece from the board.
- Castling: If the king and a rook have not moved, and there are empty spaces between them, castling is possible. The king moves two squares towards the rook, and the rook moves to the adjacent square. This move is allowed on both sides of the board.
- Promotion: When a pawn reaches the last row of the board, it can be promoted to any other piece except the king (queen, rook, knight, or bishop).
- En Passant: This mechanism is not required to be implemented in this project.
- Chess Timer: The game includes a timer with an initial time and additional time per move.

## Offline Mode

In the offline mode, players cannot move out of turn or make illegal moves. If an illegal move is attempted, it will be rejected, and the game will wait for the correct move from the same player. The additional game mechanics described above should be implemented.

Players make moves by clicking on a piece and then selecting the destination square. The current player's turn should be displayed at all times.

## Online Mode

The online mode functions similarly to the offline mode. Players take turns and make legal moves following the same rules.

