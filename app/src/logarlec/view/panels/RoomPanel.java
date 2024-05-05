package logarlec.view.panels;

import javax.swing.*;

import logarlec.model.room.Room;
import logarlec.view.elements.CustomButton;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;

import java.awt.*;

public class RoomPanel extends View{
    private Room viewedRoom;

    // the center
    private BorderLayout layout = new BorderLayout();
    private JPanel roomInfo = new JPanel();
    
    // content in the center
    private InventoryPanel inventoryPanel;
    
    public RoomPanel(Room room) {
        this.viewedRoom = room;
        inventoryPanel = new InventoryPanel(room.getInventory());

        roomInfo.setLayout(layout);
        add(roomInfo, BorderLayout.CENTER);
        
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(65, 500));
        leftPanel.setLayout(new GridLayout(4, 2));

        roomInfo.add(leftPanel, BorderLayout.WEST);
        
        // adding buttons to the left panel
        leftPanel.add(new CustomButton(IconLoader.getInstance().getIcon("student.png", 45), (e) -> {
            // TODO
        }));

        leftPanel.add(new CustomButton(IconLoader.getInstance().getIcon("professor.png", 45), (e) -> {
            // TODO
        }));

        leftPanel.add(new CustomButton(IconLoader.getInstance().getIcon("janitor.png", 45), (e) -> {
            // TODO
        }));

        leftPanel.add(new CustomButton(IconLoader.getInstance().getIcon("chest.png", 45), (e) -> {
            ReplaceCenter(inventoryPanel);
        }));

        // adding doors
        
    }

    

    public void ShowActors() {
        // TODO
    }



    @Override
    public void updateView() {
        // TODO Auto-generated method stub
    }

    private void ReplaceCenter(JPanel panel) {
        Component center = layout.getLayoutComponent(BorderLayout.CENTER);
        if (center != null) {
            remove(center);
        }

        add(panel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
