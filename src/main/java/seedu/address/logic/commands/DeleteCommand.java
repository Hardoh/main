package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Task(s): ";

    private final Index[] targetIndices;

    public DeleteCommand(Index[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();
        StringBuilder tasksDeleted = new StringBuilder(MESSAGE_DELETE_PERSON_SUCCESS);
        List<Task> toDeleteTasks = new ArrayList<>();
        for (Index targetIndex : targetIndices) {
            targetIndex.getZeroBased();
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Task taskToDelete = lastShownList.get(targetIndex.getZeroBased());
            toDeleteTasks.add(taskToDelete);
        }
        for (Task t : toDeleteTasks) {
            model.deleteTask(t);
            tasksDeleted.append(String.format("%n%s", t));
        }
        return new CommandResult(tasksDeleted.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                        && targetIndices.equals(((DeleteCommand) other).targetIndices)); // TODO check if non primitive
                                                                                         // data will
                                                                                         // be checked
    }
}
