import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controller.ExpenseTrackerController;
import model.AmountFilter;
import model.CategoryFilter;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Transaction;
import model.TransactionFilter;
import controller.InputValidation;

public class ExpenseTrackerApp {

  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });

    // Add the logic to capture the filter input and trigger filtering 

    view.getFilterButton().addActionListener(e ->{
      String filterType = view.getFilterTypeDropDown().getSelectedItem().toString();
      TransactionFilter filter ;
      if (filterType.equals("Amount")) {
        String amountStr = String.valueOf(view.getAmountFilterField());
        System.out.println(amountStr);
        if (amountStr.isEmpty()) {
          JOptionPane.showMessageDialog(view, "Please enter an amount.", "Error", JOptionPane.ERROR_MESSAGE);
          return; // Do not continue if there's no input for amount
        }

        try {
          double amount = Double.parseDouble(amountStr);
          filter = new AmountFilter(amount);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(view, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
          return; // Do not continue if there's an invalid input for amount
        }
      } else {
        String category = view.getCategoryFilterField().getText();
        filter = new CategoryFilter(category);
      }
      controller.applyFilter(filter);
    });

  }

}