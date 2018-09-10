
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.ProviderException;
import java.util.Scanner;

import factories.OperationFactory;
import interfaces.Computable;
import interfaces.Verifiable;
import utils.FileUtils;

/**
 * @author Silin Uladzislau
 *
 */
public class Runner {

	public static void main(String[] args) {
		// check if the args array is empty
		if (args.length == 0) {
			throw new ProviderException("There are no elements in the args array");
		}

		try (Scanner fileInput = new Scanner(new FileReader(new File(args[0])));
				PrintWriter fileOutput = new PrintWriter(new FileWriter(new File(args[1])), true)) {

			FileUtils.createInitialExpression(fileInput, fileOutput);
			String firstInitialString = FileUtils.getInputFileList().get(0);
			if (!fileInput.hasNextLine()) {
				fileOutput.println("There are no expressions");
				return;
			}

			while (fileInput.hasNextLine()) {
				OperationFactory.setOperation(firstInitialString);
				String expression = fileInput.nextLine();
				Verifiable<?> validator = OperationFactory.getValidator(expression);
				Computable performer = null;
				if (validator.isValidExpression()) {
					performer = OperationFactory.getPerformer(validator);
					performer.calculate();
					fileOutput.println(performer.getResultAsString());
				} else {
					fileOutput.println(validator.getInvalidMessage());
				}
			}
		}

		catch (FileNotFoundException e) {
			System.out.println("File is not found!");
		} catch (IOException e) {
			System.out.println("There is something wrong!");
		}
		catch (IndexOutOfBoundsException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}
