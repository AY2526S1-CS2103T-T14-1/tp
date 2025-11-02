---
layout: page
title: User Guide
---

StudentConnect is a **desktop app for managing student information for private tutors, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, StudentConnect can get your tuition management tasks done faster and in one place compared to using multiple traditional generic GUI apps.

The application is designed for private tutors with basic computer experience, to manage their students' information, lessons, attendance and payments. No prior technical expertise is required — the setup guide will walk you through installing Java and starting StudentConnect step by step.


* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T14-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your StudentConnect Application.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar studentconnect.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com addr/John street, block 123, #01-01` : Adds a contact named `John Doe` to the student list.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the student list.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL addr/ADDRESS [tag/TAG]…​`

**Field Constraints:**
* `NAME`: Alphanumeric characters and spaces only. Maximum 50 characters.
* `PHONE_NUMBER`: Numbers only, at least 3 digits. Maximum 20 digits.
* `EMAIL`: Valid email format. Maximum 50 characters.
* `ADDRESS`: Can be any value (should not be blank).
* `TAG`: Alphanumeric characters only. Maximum 15 characters per tag.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com addr/John street, block 123, #01-01`
* `add n/Betsy Crowe tag/friend p/1234567 e/betsycrowe@example.com addr/Newgate Prison tag/criminal `

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [addr/ADDRESS] [tag/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `tag/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Locating persons by tag: `findtag`

Finds people whose tags include any part of the keywords you enter

Format: `findtag TAG_NAME [MORE_TAGNAMES]`

> [!TIP]
> * You need not type the full tag name
> * You can use 1-n number of tag names
> * Search is case-insensitive
 
Examples:
* `findtag IMPORTANT` will search for all people with tags that have `IMPORTANT`

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Adding new lesson : `addlesson`

Adds lesson to the specified person from the address book.

Format: `addlesson INDEX n/NAME d/DAY t/TIME l/LOCATION`

* Adds lesson to the person at the specified `INDEX`.
* Overwrites any existing lesson on applied student.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* `DAY` must be either `Monday`, `Tuesday`, `Wednesday`, `Thursday`, `Friday`, `Saturday` or `Sunday`.
* `DAY` is case-insensitive.
* `TIME` must be in **hh:mm** format.

Examples:
* `addlesson 1 n/Math d/Monday t/12:00 l/RoomA` adds a lesson with name `Math` on `Monday` `1200` at `RoomA` to the 1st person in the address book.

### Marking attendance : `mark`

Marks attendance for the specified person from the address book.

Format: `mark INDEX s/STATUS`

* Marks attendance for the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* `STATUS` must be either `present` or `absent`.
* `STATUS` is case-insensitive.

Examples:
* `mark 1 s/present` marks the 1st person in the address book as `present`.

### Viewing weekly schedule : `schedule`

Shows all lessons in the current week (Mon–Sun), sorted by day and time.

Format: `schedule`

### Updating outstanding fees : `addfee`

Updates (or adds) the outstanding amount owed by a student.

Format: `addfee INDEX amt/AMOUNT`

* `INDEX` is the number of the person listed on the main GUI window.
* The amount must be a positive number up to 2 decimal places.
* The amount must be between $0.00 and $1,000,000.00 (inclusive).
* The total outstanding amount after adding cannot exceed $1,000,000.00.

Examples:
* `addfee 1 amt/150` — Adds \$150 to the first student's outstanding amount.
* `addfee 3 amt/89.50` — Adds \$89.50 to the third student's outstanding amount.

### Add payment made by student: `pay`

Add the payment made by a particular student,

Format: `pay INDEX amt/AMOUNT`

* `INDEX` is the number of the person listed on the main GUI window.
* The `AMOUNT` must be a positive number up to 2 decimal places.
* The `AMOUNT` must be between $0.00 and current owed amount (inclusive).

### Viewing payment history : `payments`

Shows the payment history (newest first).

Format: `payments`

### View outstanding payments: `outstanding`

Shows outstanding payments of all students

Format: `outstanding`

* Shows a list of people with amount owed right below the search bar

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`
 
### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Importing data file

Imports a StudentConnect data file (.json) into the app.

**How it works:**

1. Go to **Files** -> **Import**.
2. Select the .json file that you have previously exported from StudentConnect.
3. Confirm the prompt.

**Result**

On success, the data should replace the current content and a success message will appear.
On failure, an error message will appear and no data is changed.

**Note**
1. Only JSON files following StudentConnect's format will be accepted.
2. Consider creating a backup using export (See below).

### Exporting data file

Exports your current StudentConnect data to a JSON file.

**How it works:**

1. Go to **Files** -> **Export**.
2. Choose the folder you would like to save the file in.
3. StudentConnect saves a JSON file named like StudentConnect_YYYYMMDD_HHMMSS.json and shows a success message with the full path.

**Note**
1. Exported files can be used on any computer with StudentConnect.
2. Export does not change your current data.
3. You can keep multiple timestamped exports as backups.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/studentconnect.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Help** | `help`
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL addr/ADDRESS [tag/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com addr/123, Clementi Rd, 1234665 tag/friend tag/colleague`
**List** | `list`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [addr/ADDRESS] [tag/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Find by tag** | `findtag TAG_NAME [MORE_TAGNAMES]`<br> e.g., `find math important`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Add lesson** | `addlesson INDEX n/NAME d/DAY t/TIME l/LOCATION`<br> e.g., `addlesson 1 n/Math d/Monday t/12:00 l/RoomA`
**Mark attendance** | `mark INDEX s/STATUS`<br> e.g., `mark 1 s/present`
**View schedule** | `schedule`
**Add fee** | `addfee INDEX amt/AMOUNT`<br>e.g., `addfee 1 amt/150`
**Pay** | `pay INDEX amt/AMOUNT`<br> e.g., `pay 2 amt/150`
**View payment history** | `payments`
**View outstanding payments** | `outstanding`
**Import** | Menu: **File → Import** → select a valid StudentConnect JSON file
**Export** | Menu: **File → Export** → choose destination folder (file named `StudentConnect_YYYYMMDD_HHMMSS.json`)
**Clear** | `clear`
**Exit** | `exit`
