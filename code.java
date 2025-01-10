import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {
    static JFrame frame = new JFrame("Inventory");
    static JTextField inputName = new JTextField();
    static JTextField inputPrice = new JTextField();
    static JTextField inputQuantity = new JTextField();
    static JButton addProducts = new JButton("Add");
    static JButton updateProducts = new JButton("Update");
    static JButton displayProducts = new JButton("Display");
    static JTextField changedName = new JTextField();
    static JTextField changedQuantity = new JTextField();
    static JTextField changedPrice = new JTextField();
    static int numberOfProducts = 0;
    static Inventory inventory = new Inventory();
    static JLabel addingQuantity = new JLabel("Quantity of the product:");
    static JLabel addingPrice = new JLabel("Price of the product:"); //labels for update
    static JLabel addingName = new JLabel("Name of the product:");
    static JLabel adding = new JLabel("Add products here:");
    static JLabel displayNumberOfProducts = new JLabel("Number of products:");
    static JLabel displayFirstProduct = new JLabel("");
    static JLabel displaySecondProduct = new JLabel("");
    static JLabel displayThirdProduct = new JLabel("");
    static JLabel updatedProduct = new JLabel("Name of the product you want to update:");
    static JLabel updateName = new JLabel("Name of the product:");
    static JLabel updatePrice = new JLabel("Update price:");
    static JLabel updateQuantity = new JLabel("Quantity of the product:");
    static JTextField changedProduct = new JTextField("");
    static JLabel displayResultFromUpdate = new JLabel("");
    static JLabel displayResultFromAdd = new JLabel("");


    public static void main(String[] args) {
        design(frame);

        addProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(inputName.getText().isEmpty() || inputPrice.getText().isEmpty() || inputQuantity.getText().isEmpty()) {
                    displayResultFromAdd.setText("Fill in all the boxes!");
                }else {
                    numberOfProducts=addProduct(inputName, inputPrice, inputQuantity, numberOfProducts);
                }
            }
        });

        updateProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(numberOfProducts==0) {
                    displayResultFromUpdate.setText("No products in the inventory!");
                }else {
                    if (changedProduct.getText().isEmpty() || changedName.getText().isEmpty() || changedPrice.getText().isEmpty() || changedQuantity.getText().isEmpty()) {
                        displayResultFromUpdate.setText("FIll in all the boxes!");
                    } else {
                        updateProduct(changedName, changedPrice, changedQuantity, changedProduct, displayResultFromUpdate);
                    }
                }
            }
        });

        displayProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = inventory.displayAllProducts();
                System.out.println(text);
                if(text.equals("No products found")){
                    displayFirstProduct.setText("No products found");
                }else {
                    String[] myArray = text.split("\n");
                    int i = 0;
                    for (String s : myArray) {
                        if (i == 0) {
                            displayFirstProduct.setText("First product: " + s);
                        }
                        if (i == 1) {
                            displaySecondProduct.setText("Second product: " + s);
                        }
                        if (i == 2) {
                            displayThirdProduct.setText("Third product: " + s);
                        }
                        i++;
                        if (i == numberOfProducts) {
                            break;
                        }
                    }
                }
            }
        });
    }

    public static String checkInput(JTextField name,JTextField price, JTextField quantity) {
        if(name.getText().isEmpty() || price.getText().isEmpty() || quantity.getText().isEmpty()) {
            return "Fill in all the boxes!";
        }else {
            boolean isName = true;
            for (int i = 0; i < name.getText().length(); i++) {
                if ((name.getText().charAt(i) >= 'a' && name.getText().charAt(i) <= 'z')||(name.getText().charAt(i)==' ' && i!=0) || (name.getText().charAt(i) >= 'A' && name.getText().charAt(i) <= 'Z')) {
                }else{
                    isName = false;
                    return "Invalid name!";
                }
            }
            try
            {
                double v = Double.parseDouble(price.getText());
                if(v<0){
                    if (Double.parseDouble(inputPrice.getText()) < 0) {
                        return "Invalid Data";
                    }
                    return "Invalid price!";
                }
            }
            catch(NumberFormatException e)
            {
                return "Invalid price!";
            }


            try
            {
                Integer.parseInt(quantity.getText());
                if (Integer.parseInt(quantity.getText()) < 0) {
                    return "Invalid quantity";
                }
            }
            catch(NumberFormatException e)
            {
                return "Invalid quantity!";
            }
        }
        return "good";
    }

    public static int addProduct(JTextField name, JTextField price, JTextField quantity, int numberOfProducts) {
        if(numberOfProducts==0 || numberOfProducts==1 || numberOfProducts==2) {
            if (checkInput(name, price, quantity).equals("good")) {
                Product product = new Product(inputName.getText(), Double.parseDouble(inputPrice.getText()), Integer.parseInt(inputQuantity.getText()));
                inventory.addProduct(product);
                numberOfProducts++;
                displayNumberOfProducts.setText("Number of products: " + numberOfProducts);
                displayResultFromAdd.setText("Product Added!");
                name.setText("");
                price.setText("");
                quantity.setText("");
            } else {
                displayResultFromAdd.setText(checkInput(name, price, quantity));
            }

        }else{
            displayResultFromAdd.setText("The Inventory is full!");
        }
        displayFirstProduct.setText("");
        displaySecondProduct.setText("");
        displayThirdProduct.setText("");
        return numberOfProducts;
    }

    public static void updateProduct(JTextField newName, JTextField newPrice, JTextField newQuantity, JTextField oldProduct, JLabel displaying) {
        boolean exists = false;
         for (Product product : inventory.products) {
             if (product.getName().equals(changedProduct.getText())) {
                 exists = true;
                 if (checkInput(changedName, changedPrice, changedQuantity).equals("good")) {
                     inventory.updateProduct(product, changedName.getText(), Double.parseDouble(changedPrice.getText()), Integer.parseInt(changedQuantity.getText()));
                     changedProduct.setText("");
                     changedName.setText("");
                     changedQuantity.setText("");
                     changedPrice.setText("");
                     displaying.setText("Product updated successfully!");
                     break;
                 } else {
                     displayResultFromUpdate.setText(checkInput(changedName, changedPrice, changedQuantity).toString());
                 }
             }
         }
         if (!exists) {
             displayResultFromUpdate.setText("This product does not exist");
         }
         displayFirstProduct.setText("");
         displaySecondProduct.setText("");
         displayThirdProduct.setText("");
    }

    static class Product {
        String name;
        double price;
        int quantity;

        public Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
        public Product(){
            this.name = "";
            this.price = 0;
            this.quantity = 0;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String displayDetails() {
            return "Name: " + name+"    Price: " + price + "    Quantity: " + quantity;
        }
    }

    static class Inventory {
        public ArrayList<Product> products;

        public Inventory() {
            products = new ArrayList<Product>();
        }

        public void addProduct(Product product) {
            products.add(product);
            //numberOfProducts++;
        }

        public void updateProduct(Product product, String name1, double price1, int quantity1) {
            Product newProduct = new Product(name1, price1, quantity1);
            products.set(products.indexOf(product), newProduct);
        }

        public String displayAllProducts() {
            String productsList = "";
            if (!products.isEmpty()) {
                for (Product product : products) {
                    productsList+=product.displayDetails();
                    productsList+="\n";
                }
            }
            if(productsList.isEmpty()){
                return "No products found";
            }else{
                return productsList;
            }
        }
    }

    public static void design(JFrame frame) {
        frame.setSize(1000, 1000);
        frame.getContentPane().setBackground(Color.pink);
        frame.setLayout(null);

        JLabel title = new JLabel("Iva's Shop");
        title.setFont(new Font("Arial", Font.ITALIC, 40));
        title.setForeground(Color.white);
        title.setBounds(380, 10, 300, 100);
        frame.add(title);

        JTextField input = new JTextField();
        input.setBounds(200, 300, 150, 30);
        input.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        adding.setFont(new Font("Times New Roman", Font.BOLD, 20));
        adding.setForeground(Color.white);
        adding.setBounds(10, 100, 400, 30);
        frame.add(adding);

        addingName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        addingName.setForeground(Color.white);
        addingName.setBounds(10, 150, 400, 30);
        frame.add(addingName);

        inputName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        inputName.setForeground(Color.black);
        inputName.setBounds(220, 150, 150, 30);
        frame.add(inputName);

        addingPrice.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        addingPrice.setForeground(Color.white);
        addingPrice.setBounds(10, 200, 400, 30);
        frame.add(addingPrice);

        inputPrice.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        inputPrice.setForeground(Color.black);
        inputPrice.setBounds(210, 200, 150, 30);
        frame.add(inputPrice);

        addingQuantity.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        addingQuantity.setForeground(Color.white);
        addingQuantity.setBounds(10, 250, 400, 30);
        frame.add(addingQuantity);

        inputQuantity.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        inputQuantity.setForeground(Color.black);
        inputQuantity.setBounds(240, 250, 150, 30);
        frame.add(inputQuantity);

        addProducts.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        addProducts.setForeground(Color.gray);
        addProducts.setBounds(20, 300, 150, 50);
        frame.add(addProducts);

        updateProducts.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        updateProducts.setForeground(Color.gray);
        updateProducts.setBounds(20, 600, 150, 50);
        frame.add(updateProducts);

        displayProducts.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        displayProducts.setForeground(Color.gray);
        displayProducts.setBounds(20, 700, 150, 50);
        frame.add(displayProducts);

        changedProduct.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        changedProduct.setForeground(Color.gray);
        changedProduct.setBounds(450, 400, 150, 30);
        frame.add(changedProduct);

        changedName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        changedName.setForeground(Color.gray);
        changedName.setBounds(225, 450, 150, 30);
        frame.add(changedName);

        changedPrice.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        changedPrice.setForeground(Color.gray);
        changedPrice.setBounds(150, 500, 150, 30);
        frame.add(changedPrice);

        changedQuantity.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        changedQuantity.setForeground(Color.gray);
        changedQuantity.setBounds(250, 550, 150, 30);
        frame.add(changedQuantity);

        displayNumberOfProducts.setText("Number of products: " + numberOfProducts);
        displayNumberOfProducts.setFont(new Font("Times New Roman", Font.BOLD, 20));
        displayNumberOfProducts.setForeground(Color.gray);
        displayNumberOfProducts.setBounds(750, 100, 300, 30);
        frame.add(displayNumberOfProducts);

        displayFirstProduct.setFont(new Font("Times New Roman", Font.BOLD, 20));
        displayFirstProduct.setForeground(Color.white);
        displayFirstProduct.setBounds(10, 770, 600, 30);
        frame.add(displayFirstProduct);

        displaySecondProduct.setFont(new Font("Times New Roman", Font.BOLD, 20));
        displaySecondProduct.setForeground(Color.white);
        displaySecondProduct.setBounds(10, 820, 600, 30);
        frame.add(displaySecondProduct);

        displayThirdProduct.setFont(new Font("Times New Roman", Font.BOLD, 20));
        displayThirdProduct.setForeground(Color.white);
        displayThirdProduct.setBounds(10, 870, 600, 30);
        frame.add(displayThirdProduct);

        updatedProduct.setFont(new Font("Times New Roman", Font.BOLD, 20));
        updatedProduct.setForeground(Color.white);
        updatedProduct.setBounds(10, 400, 500, 30);
        frame.add(updatedProduct);

        updateName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        updateName.setForeground(Color.white);
        updateName.setBounds(10, 450, 250, 30);
        frame.add(updateName);

        updatePrice.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        updatePrice.setForeground(Color.white);
        updatePrice.setBounds(10, 500, 250, 30);
        frame.add(updatePrice);

        updateQuantity.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        updateQuantity.setForeground(Color.white);
        updateQuantity.setBounds(10, 550, 250, 30);
        frame.add(updateQuantity);

        displayResultFromUpdate.setFont(new Font("Times New Roman", Font.BOLD, 20));
        displayResultFromUpdate.setForeground(Color.white);
        displayResultFromUpdate.setBounds(200, 610, 600, 30);
        frame.add(displayResultFromUpdate);

        displayResultFromAdd.setFont(new Font("Times New Roman", Font.BOLD, 20));
        displayResultFromAdd.setForeground(Color.white);
        displayResultFromAdd.setBounds(200, 310, 600, 30);
        frame.add(displayResultFromAdd);

        frame.setVisible(true);
    }
}
