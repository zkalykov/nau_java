# Telegram Expense Tracker Bot

## Introduction
This project is a Telegram bot developed to help users track their financial transactions through a simple chat interface. Users can add, edit, delete, and categorize their transactions, as well as view summaries of their financial activities.

## Prerequisites
To run this bot, you need:
- Python 3.6 or later
- Pip (Python package installer)

## Installation

### Step 1: Clone the Repository
First, clone this repository to your local machine using:

git clone <repository-url>

mathematica

Replace `<repository-url>` with the URL of the repository.

### Step 2: Install Dependencies
Install the required Python libraries using pip:

pip3 install telepot
pip3 install urllib3

vbnet


If you're behind a proxy, you might need to configure urllib3 to use the proxy server. Uncomment and adjust the proxy settings in the script as necessary.

### Step 3: Set Up Telegram Bot
1. Talk to [BotFather](https://t.me/botfather) on Telegram to create a new bot.
2. Obtain the bot token from BotFather and replace the placeholder in the script with your actual bot token.

### Step 4: Download SSL Certificates (Mac & Linux)
If you encounter SSL errors, you may need to ensure your certificates are up-to-date:

/Applications/Python\ 3.12/Install\ Certificates.command

vbnet

Adjust the path depending on your Python installation directory and version.

## Running the Bot
To run the bot, navigate to the directory containing your script and run:

python3 bot_script.py

markdown

Replace `bot_script.py` with the name of your Python script.

## Features
- **Transaction Management**: Add, edit, and delete transactions.
- **Categorization**: Automatically categorize transactions based on predefined keywords or manually select a category.
- **Summary**: View a breakdown of expenses by category or overall balance.

## Commands
- `/start` - Start interaction with the bot and see introductory instructions.
- `/categories` - View expenses categorized.
- `/transactions` - Display all recorded transactions.
- `/edit` - Edit a specific transaction.
- `/delete` - Delete a specific transaction.
- `/clear_data` - Clear all stored data (with confirmation).

## Support
For support, contact the repository maintainer or open an issue on the project's issue tracker.

## Contribution
Contributions are welcome! For major changes, please open an issue first to discuss what you would like to change.

Please ensure to update tests as appropriate.
