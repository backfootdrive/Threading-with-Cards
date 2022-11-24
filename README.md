# Card Game with Threading

This is a card game that implements threading, inputs are taken via the command-line.

## Rules

A game has _n_ number of players and decks, with a player being able to hold 4 cards in their hand. At the beginning of the game cards are distributed in a round-robin fashion, with the remaining cards distributed among the decks (again in a round-robin fashion). A card pack must contain _8n_ cards, so each deck and hand contains 4 cards at the beginning of the game.

To win a player needs 4 cards of the same value in their hand. If there is not winning starting hand players discard a card to the bottom of the deck on their right before picking up a card from the top of the deck on their left (each player does this simultaneously through threading). This repeats until a player declares they have won at which point the game ends.

## Executing the JAR

1.  Using a command-line/terminal navigate to the location __cards.jar__ has been downloaded
2.  Run the command `java -jar cards.jar`
3.  Enter the number of player  (e.g., '16')
4.  Enter the filename of your card pack ('e.g., example.txt')
    - Recommended for card pack to be in the same directory as cards.jar
    - Use `./SUBDIRECTORY/example.txt` if it is located in a sub-directory
    - May also use absolute path (e.g., 'C:\Users\USERNAME\4.txt')

The game will then start, and the actions and winner will be displayed in the terminal

The actions of each player and contents of each deck at the end of the game will be outputed as txt files in the 'gameOutput' folder

## Running Source Code

1. In the command-line/terminal navigate to your download location
2. Open the bin folder using `cd ECM2414-Coursework/bin`
3. Run the command `java CardGame`
   - Note you need to have you pack file in the bin folder or use the absolute path (e.g., 'C:\Users\USERNAME\4.txt')

## Testing the Code

To run the test suite, you will need to open the project in VSCode and run the tests using the Test Runner Extension


### Visual Studio Code

1. Install [Visual Studio Code](https://code.visualstudio.com/download) for your OS
2. In VSCode File > Open Folder > Open the 'ECM2414-Coursework' folder.
3. In VSCode open extensions (CTRL+SHIFT+X) and install  [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
4. In the 'Testing' menu (look for a conical flask icon on the sidebar) right click on the 'ECM2414 Coursework' panel and click 'Run Test' or press the play icon.
5. All tests should have a green check mark next to them

![Example](/res/Example.PNG)

## Authors

- Kamran Haque
- Tyler Allen

## License

[@MIT](https://github.com/backfootdrive/Threading-with-Cards/blob/main/LICENSE.md)
