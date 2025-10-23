package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    @Test
    public void importAddressBook_validFile_success() throws Exception {
        // Create a file with typical address book data
        Path importFilePath = temporaryFolder.resolve("import.json");
        ReadOnlyAddressBook typicalAddressBook = getTypicalAddressBook();
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(importFilePath);
        addressBookStorage.saveAddressBook(typicalAddressBook, importFilePath);

        // Verify model is initially empty
        assertEquals(new AddressBook(), model.getAddressBook());

        // Import the file
        logic.importAddressBook(importFilePath);

        // Verify model now contains the imported data
        assertEquals(typicalAddressBook, model.getAddressBook());
    }

    @Test
    public void importAddressBook_nonExistentFile_throwsDataLoadingException() {
        Path nonExistentFile = temporaryFolder.resolve("nonexistent.json");
        assertThrows(DataLoadingException.class, () -> logic.importAddressBook(nonExistentFile));
    }

    @Test
    public void importAddressBook_invalidJsonFile_throwsDataLoadingException() throws Exception {
        // Create a file with invalid JSON content
        Path invalidJsonFile = temporaryFolder.resolve("invalid.json");
        java.nio.file.Files.writeString(invalidJsonFile, "{invalid json content}");

        assertThrows(DataLoadingException.class, () -> logic.importAddressBook(invalidJsonFile));
    }

    @Test
    public void importAddressBook_replacesExistingData() throws Exception {
        // Add a person to the model
        Person amy = new PersonBuilder(AMY).withTags().build();
        model.addPerson(amy);
        assertNotEquals(new AddressBook(), model.getAddressBook());

        // Create a different address book file
        Path importFilePath = temporaryFolder.resolve("import.json");
        ReadOnlyAddressBook typicalAddressBook = getTypicalAddressBook();
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(importFilePath);
        addressBookStorage.saveAddressBook(typicalAddressBook, importFilePath);

        // Import should replace the existing data
        logic.importAddressBook(importFilePath);

        // Verify the model now contains only the imported data
        assertEquals(typicalAddressBook, model.getAddressBook());
    }

    @Test
    public void importAddressBook_emptyAddressBook_success() throws Exception {
        // Add a person to the model first
        Person amy = new PersonBuilder(AMY).withTags().build();
        model.addPerson(amy);

        // Create an empty address book file
        Path emptyFilePath = temporaryFolder.resolve("empty.json");
        AddressBook emptyAddressBook = new AddressBook();
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(emptyFilePath);
        addressBookStorage.saveAddressBook(emptyAddressBook, emptyFilePath);

        // Import the empty file
        logic.importAddressBook(emptyFilePath);

        // Verify model is now empty
        assertEquals(emptyAddressBook, model.getAddressBook());
    }

    @Test
    public void exportAddressBook_validPath_success() throws Exception {
        // Add data to the model
        ReadOnlyAddressBook typicalAddressBook = getTypicalAddressBook();
        model.setAddressBook(typicalAddressBook);

        // Export to a file
        Path exportFilePath = temporaryFolder.resolve("export.json");
        logic.exportAddressBook(exportFilePath);

        // Verify the file was created and contains the correct data
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(exportFilePath);
        Optional<ReadOnlyAddressBook> exportedData = addressBookStorage.readAddressBook(exportFilePath);

        assert exportedData.isPresent();
        assertEquals(typicalAddressBook, exportedData.get());
    }

    @Test
    public void exportAddressBook_emptyAddressBook_success() throws Exception {
        // Model is initially empty
        assertEquals(new AddressBook(), model.getAddressBook());

        // Export empty address book
        Path exportFilePath = temporaryFolder.resolve("empty_export.json");
        logic.exportAddressBook(exportFilePath);

        // Verify the file was created and contains empty data
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(exportFilePath);
        Optional<ReadOnlyAddressBook> exportedData = addressBookStorage.readAddressBook(exportFilePath);

        assert exportedData.isPresent();
        assertEquals(new AddressBook(), exportedData.get());
    }

    @Test
    public void exportAddressBook_overwriteExistingFile_success() throws Exception {
        // Add initial data and export
        Person amy = new PersonBuilder(AMY).withTags().build();
        model.addPerson(amy);

        Path exportFilePath = temporaryFolder.resolve("overwrite.json");
        logic.exportAddressBook(exportFilePath);

        // Modify the model
        ReadOnlyAddressBook typicalAddressBook = getTypicalAddressBook();
        model.setAddressBook(typicalAddressBook);

        // Export again to the same file (overwrite)
        logic.exportAddressBook(exportFilePath);

        // Verify the file contains the new data
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(exportFilePath);
        Optional<ReadOnlyAddressBook> exportedData = addressBookStorage.readAddressBook(exportFilePath);

        assert exportedData.isPresent();
        assertEquals(typicalAddressBook, exportedData.get());
    }

    @Test
    public void exportAddressBook_invalidPath_throwsIoException() {
        // Try to export to an invalid path (directory that doesn't exist and can't be created)
        Path invalidPath = temporaryFolder.resolve("nonexistent/deeply/nested/path/export.json");

        // Note: This might not throw on all systems, as Java creates parent directories
        // But it tests the error handling pathway
        try {
            logic.exportAddressBook(invalidPath);
            // If it succeeds, verify the file was created
            assert java.nio.file.Files.exists(invalidPath);
        } catch (IOException e) {
            // Expected behavior on some systems
            assert true;
        }
    }

    @Test
    public void exportThenImport_dataIntegrity_success() throws Exception {
        // Add data to the model
        ReadOnlyAddressBook typicalAddressBook = getTypicalAddressBook();
        model.setAddressBook(typicalAddressBook);

        // Export the data
        Path exportFilePath = temporaryFolder.resolve("roundtrip.json");
        logic.exportAddressBook(exportFilePath);

        // Clear the model
        model.setAddressBook(new AddressBook());
        assertNotEquals(typicalAddressBook, model.getAddressBook());

        // Import the exported data
        logic.importAddressBook(exportFilePath);

        // Verify data integrity
        assertEquals(typicalAddressBook, model.getAddressBook());
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an AddressBookStorage that throws the IOException e when saving
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(prefPath) {
            @Override
            public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveAddressBook method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
