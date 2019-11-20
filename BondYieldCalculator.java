import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
class Calc{
    ArrayList<JTextField> priceFields = new ArrayList<>();
    ArrayList<JTextField> yieldFields = new ArrayList<>();

    public static void main(String args[]){  
        Calc demo = new Calc();
        demo.createWindow();
    }
    
    double calculatePrice() {
        double coupon = Double.parseDouble(priceFields.get(0).getText());
        int years = Integer.parseInt(priceFields.get(1).getText());
        double face = Double.parseDouble(priceFields.get(2).getText());
        double rate = Double.parseDouble(priceFields.get(3).getText());

        double discPrice = coupon*face;
        double disc = 1 + rate;
        double solution = 0;   

        solution += ((face) / Math.pow(disc, years));
        for (int i = 1; i <= years; i++) {
            solution += (discPrice / Math.pow(disc, i));
        }
        
        return solution;
    }

    double calculatePrice(ArrayList<String> x) {
        double coupon = Double.parseDouble(x.get(0));
        int years = Integer.parseInt(x.get(1));
        double face = Double.parseDouble(x.get(2));
        double rate = Double.parseDouble(x.get(3));

        double discPrice = coupon*face;
        double disc = 1 + rate;
        double solution = 0;   

        solution += ((face) / Math.pow(disc, years));
        for (int i = 1; i <= years; i++) {
            solution += (discPrice / Math.pow(disc, i));
        }
        
        return solution;
    }

    double calculateYield() {
        double coupon = Double.parseDouble(yieldFields.get(0).getText());
        int years = Integer.parseInt(yieldFields.get(1).getText());
        double face = Double.parseDouble(yieldFields.get(2).getText());
        double price = Double.parseDouble(yieldFields.get(3).getText()); 

        double discForInvestor = face - price;
        double discPrice = coupon*face;
        double solution = 0;

        // approximate yield to maturity
        solution = (((discPrice) + (discForInvestor/years)) / ((face+price)/2));

        ArrayList<String> tempList = new ArrayList<>();

        String couponP = String.valueOf(coupon);
        String yearsP = String.valueOf(years);
        String faceP = String.valueOf(face);
        String rateP = String.valueOf(solution);

        tempList.add(couponP);
        tempList.add(yearsP);
        tempList.add(faceP);
        tempList.add(rateP);
       
        Set<Double> set = new HashSet<>();
        double maxDiff = .00000001;

        // if price from the estimated rate is higher than what we wanted
        // that means rate is too low and vice versa
        // we do trial and error here to try to get it as close as possible
        while (Math.abs(calculatePrice(tempList) - price) > maxDiff) {
            if (calculatePrice(tempList) > price) {
                solution += .00000001;
            } else {
                solution -= .00000001;
            }
            rateP = String.valueOf(solution);
            tempList.set(3, rateP);
            if (set.contains(solution)) break;
            set.add(solution);
        }
        
        return solution;
    }

    void createWindow() {
        JFrame frame = new JFrame("Bond Yield Calculator by Hugh Leow");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(700,350);
        frame.setResizable(false);

        JPanel container = new JPanel();
        JPanel price = new JPanel();
        price.setLayout(new BoxLayout(price, BoxLayout.PAGE_AXIS));
        JPanel yield = new JPanel();
        yield.setLayout(new BoxLayout(yield, BoxLayout.PAGE_AXIS));        

        container.setLayout(new GridLayout(1,2));
        container.add(price);
        container.add(yield);

        frame.getContentPane().add(container);

        // calculate price components
        JLabel titlePrice = new JLabel("Calculate Price");
        titlePrice.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JLabel couponPriceLabel = new JLabel("Coupon");
        couponPriceLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JTextField couponPriceText = new JTextField("Enter coupon");
        priceFields.add(couponPriceText);
        
        JLabel yearsPriceLabel = new JLabel("Time (Years)");
        yearsPriceLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JTextField yearsPriceText = new JTextField("Enter amount of time (years)");
        priceFields.add(yearsPriceText);        
        
        JLabel valuePriceLabel = new JLabel("Value");
        valuePriceLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JTextField valuePriceText = new JTextField("Enter face value");
        priceFields.add(valuePriceText);        
        
        JLabel ratePriceLabel = new JLabel("Rate");
        ratePriceLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JTextField ratePriceText = new JTextField("Enter rate");
        priceFields.add(ratePriceText);        

        JLabel priceSolution = new JLabel("Answer");
        priceSolution.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JButton calculatePriceButton = new JButton("Calculate Price");

        calculatePriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double answer = calculatePrice();
                priceSolution.setText(String.valueOf(answer));
            }
        });

        price.add(titlePrice);
        price.add(Box.createRigidArea(new Dimension(0, 40)));        
        price.add(couponPriceLabel);
        price.add(couponPriceText);
        price.add(yearsPriceLabel);
        price.add(yearsPriceText);
        price.add(valuePriceLabel);
        price.add(valuePriceText);
        price.add(ratePriceLabel);
        price.add(ratePriceText);
        price.add(Box.createRigidArea(new Dimension(0, 30))); 
        price.add(priceSolution);
        price.add(Box.createRigidArea(new Dimension(0, 30)));
        price.add(calculatePriceButton);

        // calculate yield components
        JLabel titleYield = new JLabel("Calculate Yield");
        titleYield.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JLabel couponYieldLabel = new JLabel("Coupon");
        couponYieldLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JTextField couponYieldText = new JTextField("Enter coupon");
        yieldFields.add(couponYieldText);        
        
        JLabel yearsYieldLabel = new JLabel("Time (Years)");
        yearsYieldLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JTextField yearsYieldText = new JTextField("Enter amount of time (years)");
        yieldFields.add(yearsYieldText);        
        
        JLabel valueYieldLabel = new JLabel("Value");
        valueYieldLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JTextField valueYieldText = new JTextField("Enter face value");
        yieldFields.add(valueYieldText);        
        
        JLabel priceYieldLabel = new JLabel("Price");
        priceYieldLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JTextField priceYieldText = new JTextField("Enter price");
        yieldFields.add(priceYieldText);        

        JLabel yieldSolution = new JLabel("Answer");
        yieldSolution.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JButton calculateYieldButton = new JButton("Calculate Yield");

        calculateYieldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double answer = calculateYield();
                yieldSolution.setText(String.valueOf(answer));
            }
        });

        yield.add(titleYield);
        yield.add(Box.createRigidArea(new Dimension(0, 40)));        
        yield.add(couponYieldLabel);
        yield.add(couponYieldText);
        yield.add(yearsYieldLabel);
        yield.add(yearsYieldText);
        yield.add(valueYieldLabel);
        yield.add(valueYieldText);
        yield.add(priceYieldLabel);
        yield.add(priceYieldText);
        yield.add(Box.createRigidArea(new Dimension(0, 30)));  
        yield.add(yieldSolution);
        yield.add(Box.createRigidArea(new Dimension(0, 30)));
        yield.add(calculateYieldButton);

        frame.setVisible(true);        
    }
}
