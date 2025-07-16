# HustleEmpire

A text-based console game built with Java 17, where you embody a budding entrepreneur, navigating various markets to buy low, sell high, and build your bankroll to become a true "Hustle Empire" legend!

## Features

* **Player Customization:** Create your unique hustler, with an option for an auto-generated name.
* **Skill Allocation:** Distribute initial skill points between:
    * **Bargaining:** Get better deals when buying items.
    * **Market Sense:** Maximize your profit when selling items.
* **Dynamic Gameplay Loop:** Engage in continuous rounds of buying and selling until you hit the win condition (reach $500 bankroll) or the lose condition (bankroll drops to $0).
* **Leveling System:** Gain experience (XP) with each successful transaction, level up, and incrementally improve your skills.
* **Interactive Scenes:**
    * **Yard Sale:** Start small, finding everyday items and honing your bargaining skills.
    * **Flea Market:** Step up your game with higher-value items, offering bigger risks and rewards.
    * **Estate Auction:** Step into the grand halls of an estate auction, where hidden treasures await the keen eye and swift bid!
* **Randomized Outcomes:** Experience the unpredictable nature of the market with random item selection, price fluctuations, and occasional "loss events."
* **Bid Against NPC:** Test your skills while bidding for high value items against Non Player Characters. 
* **Replayability:** Option to restart a new game session after winning or losing.

## How to Run

### Prerequisites
* Java Development Kit (JDK) 17 or higher installed on your system.

### Steps
1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/jeffgsherwood/hustle-empire.git
    ```
2.  **Navigate to the Project Directory:**
    ```bash
    cd hustle-empire
    ```
3.  **Compile the Source Code:**
    ```bash
    javac -d . src/com/jeffsherwood/hustleempire/*.java
    ```
    *(Note: The `-d .` compiles into the current directory, placing the `com` package folder directly. If your `src` folder is the root of your project, you'd adjust this slightly, but this is a common setup.)*
4.  **Run the Game:**
    ```bash
    java com.jeffsherwood.hustleempire.Main
    ```

## Project Status / To-Do

* **Core Gameplay Loop:** Implemented and functional.
* **Player Management:** Fully implemented (name, bankroll, skills, level, XP).
* **Playable Scenes:**
    * Yard Sale (Functional)
    * Flea Market (Functional)
    * Estate Auction (Functional)
* **Future Scenes (Coming Soon):**
    * Thrift Store
    * Retail Arbitrage
* **Inventory System:** Currently, items are bought and sold within the same scene. Future enhancement to allow players to hold items and sell them at a later time/location.
* **More Item Variety & Dynamics:** Expand item lists and introduce more complex item characteristics (e.g., condition, rarity).


---