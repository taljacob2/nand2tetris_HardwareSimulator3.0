/********************************************************************************
 * The contents of this file are subject to the GNU General Public License      *
 * (GPL) Version 2 or later (the "License"); you may not use this file except   *
 * in compliance with the License. You may obtain a copy of the License at      *
 * http://www.gnu.org/copyleft/gpl.html                                         *
 *                                                                              *
 * Software distributed under the License is distributed on an "AS IS" basis,   *
 * without warranty of any kind, either expressed or implied. See the License   *
 * for the specific language governing rights and limitations under the         *
 * License.                                                                     *
 *                                                                              *
 * This file was originally developed as part of the software suite that        *
 * supports the book "The Elements of Computing Systems" by Nisan and Schocken, *
 * MIT Press 2005. If you modify the contents of this file, please document and *
 * mark your changes clearly, for the benefit of others.                        *
 ********************************************************************************/

package SimulatorsGUI;

import Colors.Colors;
import HackGUI.*;
import Hack.Utilities.Conversions;
import ResolutionUtils.ResolutionUtils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.*;

/**
 * This class represents a 16-bits binary number.
 */

//TODO: THIS CLASS IS THE GUI OF THE BINARY USER INTERFACE WHEN THE USER WANTS TO TYPE A BINARY!
public class BinaryComponent extends JPanel implements MouseListener, KeyListener {

    // Creating the text fields.
    private JTextField bit0 = new JTextField(1);
    private JTextField bit1 = new JTextField(1);
    private JTextField bit2 = new JTextField(1);
    private JTextField bit3 = new JTextField(1);
    private JTextField bit4 = new JTextField(1);
    private JTextField bit5 = new JTextField(1);
    private JTextField bit6 = new JTextField(1);
    private JTextField bit7 = new JTextField(1);
    private JTextField bit8 = new JTextField(1);
    private JTextField bit9 = new JTextField(1);
    private JTextField bit10 = new JTextField(1);
    private JTextField bit11 = new JTextField(1);
    private JTextField bit12 = new JTextField(1);
    private JTextField bit13 = new JTextField(1);
    private JTextField bit14 = new JTextField(1);
    private JTextField bit15 = new JTextField(1);

    // An array containing all of the text fields.
    private JTextField[] bits = new JTextField[16];

    // The value of this component in a String representation.
    private StringBuffer valueStr;

    // Creating buttons.
    private JButton okButton = new JButton();
    private JButton cancelButton = new JButton();

    // Creating icons.
    private ImageIcon okIcon = new ImageIcon(Utilities.imagesDir + "smallok.gif");
    private ImageIcon cancelIcon = new ImageIcon(Utilities.imagesDir + "smallcancel.gif");

    // A vector conatining the listeners to this component.
    private Vector listeners;

    // A boolean value which is true if the user pressed the ok button and
    // false otherwise.
    private boolean isOk = false;

    // The border of this component.
    private Border binaryBorder;

    // The number of available bits.
    private int numberOfBits;

    /**
     * Constructs a new BinaryComponent.
     */
    public BinaryComponent() {
        listeners = new Vector();

        jbInit();
    }

    /**
     * Registers the given PinValueListener as a listener to this component.
     */
    public void addListener(PinValueListener listener) {
        listeners.addElement(listener);
    }

    /**
     * Un-registers the given PinValueListener from being a listener to this component.
     */
    public void removeListener(PinValueListener listener) {
        listeners.removeElement(listener);
    }

    /**
     * Notify all the PinValueListeners on actions taken in it, by creating a
     * PinValueEvent and sending it using the pinValueChanged method to all
     * of the listeners.
     */
    public void notifyListeners() {
        PinValueEvent event = new PinValueEvent(this, valueStr.toString(), isOk);
        for (int i = 0; i < listeners.size(); i++) {
            ((PinValueListener) listeners.elementAt(i)).pinValueChanged(event);
        }
    }

    /**
     * Sets the number of bits of this component.
     */
    public void setNumOfBits(int num) {
        numberOfBits = num;
        for (int i = 0; i < bits.length; i++) {
            if (i < bits.length - num) {
                bits[i].setText("");
                bits[i].setBackground(Colors.getInstance().binaryUIBackgroundDisabled);
                bits[i].setEnabled(false);
            }
            else {
                bits[i].setBackground(Colors.getInstance().binaryUIBackgroundEnabled);
                bits[i].setEnabled(true);
            }
        }
    }

    /**
     * Sets the value of this component.
     */
    public void setValue(short value) {
        valueStr = new StringBuffer(Conversions.decimalToBinary(value, 16));
        for (int i = 0; i < bits.length; i++) {
            bits[i].setText(String.valueOf(valueStr.charAt(i)));
        }
    }

    /**
     * Returns the value of this component.
     */
    public short getValue() {
        return (short) Conversions.binaryToInt(valueStr.toString());
    }

    // Updates the value of this component.
    private void updateValue() {
        valueStr = new StringBuffer(16);
        char currentChar;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i].getText().equals(""))
                currentChar = '0';
            else
                currentChar = bits[i].getText().charAt(0);
            valueStr.append(currentChar);
        }
    }

    /**
     * Implementing the action of double-clicking the mouse on the text field.
     * "0" --> "1"
     * "1" --> "0"
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JTextField t = (JTextField) e.getSource();
            if (t.getText().equals("0"))
                t.setText("1");
            else if (t.getText().equals("1"))
                t.setText("0");
        }
    }

    /**
     * Implementing the action of inserting a letter into the text field,
     * or pressing enter / escape.
     */
    public void keyTyped(KeyEvent e) {
        JTextField t = (JTextField) e.getSource();
        if (e.getKeyChar() == '0' || e.getKeyChar() == '1') {
            t.transferFocus();
            t.selectAll();
        }
        else if (e.getKeyChar() == Event.ENTER) {
            approve();
        }
        else if (e.getKeyChar() == Event.ESCAPE) {
            hideBinary();
        }
        else {
            t.selectAll();
            t.getToolkit().beep();
        }
    }

    // Empty implementations
    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    // Initialization of this component.
    //TODO: IMPLEMENTING THE POSITION OF EACH RECTANGLE IN THE Binary GUI !!!
    private void jbInit() {
        binaryBorder = BorderFactory.createLineBorder(Colors.getInstance().binaryUILineBorder, 3);
        this.setLayout(null);
        bit0.setFont(Utilities.valueFont);
        bit0.setText("0");
        bit0.setHorizontalAlignment(SwingConstants.RIGHT);
        bit0.setBounds(new Rectangle((int) (211 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit0.addMouseListener(this);
        bit0.addKeyListener(this);
        bit1.setFont(Utilities.valueFont);
        bit1.setText("0");
        bit1.setHorizontalAlignment(SwingConstants.RIGHT);
        bit1.setBounds(new Rectangle((int) (198 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit1.addMouseListener(this);
        bit1.addKeyListener(this);
        bit2.setFont(Utilities.valueFont);
        bit2.setText("0");
        bit2.setHorizontalAlignment(SwingConstants.RIGHT);
        bit2.setBounds(new Rectangle((int) (185 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit2.addMouseListener(this);
        bit2.addKeyListener(this);
        bit3.setFont(Utilities.valueFont);
        bit3.setText("0");
        bit3.setHorizontalAlignment(SwingConstants.RIGHT);
        bit3.setBounds(new Rectangle((int) (172 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit3.addMouseListener(this);
        bit3.addKeyListener(this);
        bit4.setFont(Utilities.valueFont);
        bit4.setText("0");
        bit4.setHorizontalAlignment(SwingConstants.RIGHT);
        bit4.setBounds(new Rectangle((int) (159 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit4.addMouseListener(this);
        bit4.addKeyListener(this);
        bit5.setFont(Utilities.valueFont);
        bit5.setText("0");
        bit5.setHorizontalAlignment(SwingConstants.RIGHT);
        bit5.setBounds(new Rectangle((int) (146 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit5.addMouseListener(this);
        bit5.addKeyListener(this);
        bit6.setFont(Utilities.valueFont);
        bit6.setText("0");
        bit6.setHorizontalAlignment(SwingConstants.RIGHT);
        bit6.setBounds(new Rectangle((int) (133 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit6.addMouseListener(this);
        bit6.addKeyListener(this);
        bit7.setFont(Utilities.valueFont);
        bit7.setText("0");
        bit7.setHorizontalAlignment(SwingConstants.RIGHT);
        bit7.setBounds(new Rectangle((int) (120 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit7.addMouseListener(this);
        bit7.addKeyListener(this);
        bit8.setFont(Utilities.valueFont);
        bit8.setText("0");
        bit8.setHorizontalAlignment(SwingConstants.RIGHT);
        bit8.setBounds(new Rectangle((int) (107 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit8.addMouseListener(this);
        bit8.addKeyListener(this);
        bit9.setFont(Utilities.valueFont);
        bit9.setText("0");
        bit9.setHorizontalAlignment(SwingConstants.RIGHT);
        bit9.setBounds(new Rectangle((int) (94 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit9.addMouseListener(this);
        bit9.addKeyListener(this);
        bit10.setFont(Utilities.valueFont);
        bit10.setText("0");
        bit10.setHorizontalAlignment(SwingConstants.RIGHT);
        bit10.setBounds(new Rectangle((int) (81 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit10.addMouseListener(this);
        bit10.addKeyListener(this);
        bit11.setFont(Utilities.valueFont);
        bit11.setText("0");
        bit11.setHorizontalAlignment(SwingConstants.RIGHT);
        bit11.setBounds(new Rectangle((int) (68 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit11.addMouseListener(this);
        bit11.addKeyListener(this);
        bit12.setFont(Utilities.valueFont);
        bit12.setText("0");
        bit12.setHorizontalAlignment(SwingConstants.RIGHT);
        bit12.setBounds(new Rectangle((int) (55 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit12.addMouseListener(this);
        bit12.addKeyListener(this);
        bit13.setFont(Utilities.valueFont);
        bit13.setText("0");
        bit13.setHorizontalAlignment(SwingConstants.RIGHT);
        bit13.setBounds(new Rectangle((int) (42 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit13.addMouseListener(this);
        bit13.addKeyListener(this);
        bit14.setFont(Utilities.valueFont);
        bit14.setText("0");
        bit14.setHorizontalAlignment(SwingConstants.RIGHT);
        bit14.setBounds(new Rectangle((int) (29 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit14.addMouseListener(this);
        bit14.addKeyListener(this);
        bit15.setFont(Utilities.valueFont);
        bit15.setText("0");
        bit15.setHorizontalAlignment(SwingConstants.RIGHT);
        bit15.setBounds(new Rectangle((int) (16 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 8, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        bit15.addMouseListener(this);
        bit15.addKeyListener(this);
        okButton.setHorizontalTextPosition(SwingConstants.CENTER);
        okButton.setIcon(okIcon);
        okButton.setBounds(new Rectangle((int) (97 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (29 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (23 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (20 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButton_actionPerformed(e);
            }
        });
        cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
        cancelButton.setIcon(cancelIcon);
        cancelButton.setBounds(new Rectangle((int) (125 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (29 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (23 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (20 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton_actionPerformed(e);
            }
        });
        this.setBorder(binaryBorder);
        this.add(bit15, null);
        this.add(bit14, null);
        this.add(bit13, null);
        this.add(bit12, null);
        this.add(bit11, null);
        this.add(bit10, null);
        this.add(bit9, null);
        this.add(bit8, null);
        this.add(bit7, null);
        this.add(bit6, null);
        this.add(bit5, null);
        this.add(bit4, null);
        this.add(bit3, null);
        this.add(bit2, null);
        this.add(bit1, null);
        this.add(bit0, null);
        this.add(cancelButton, null);
        this.add(okButton, null);

        bits[0] = bit15;
        bits[1] = bit14;
        bits[2] = bit13;
        bits[3] = bit12;
        bits[4] = bit11;
        bits[5] = bit10;
        bits[6] = bit9;
        bits[7] = bit8;
        bits[8] = bit7;
        bits[9] = bit6;
        bits[10] = bit5;
        bits[11] = bit4;
        bits[12] = bit3;
        bits[13] = bit2;
        bits[14] = bit1;
        bits[15] = bit0;

    }

    /**
     * Approve the change (called when OK is pressed or ENTER is pressed
     */
    private void approve() {
        isOk = true;
        updateValue();
        notifyListeners();
        setVisible(false);
    }

    /**
     * Implementing the action of pressing the ok button.
     */
    public void okButton_actionPerformed(ActionEvent e) {
        approve();
    }

    /**
     * Implementing the action of pressing the cancel button.
     */
    public void cancelButton_actionPerformed(ActionEvent e) {
        hideBinary();
    }

    /**
     * Hides the binary component as though the cancel button was pressed
     */
    public void hideBinary() {
        isOk = false;
        notifyListeners();
        setVisible(false);
    }

    /**
     * Shows the Binary component and gives focus to the first available bit.
     */
    public void showBinary() {
        setVisible(true);
        bits[16 - numberOfBits].grabFocus();
    }
}
