package logarlec.view.panels;

import javax.swing.*;

import logarlec.control.rendering.ItemHolderViewFactory;
import logarlec.model.room.Door;
import logarlec.model.room.Room;
import logarlec.view.elements.CustomButton;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;
import logarlec.view.utility.ThemeManager;
import logarlec.view.elements.ScrollUI;
import java.util.ArrayList;

import java.awt.*;
/**
 * A panel displaying a room
 */
public class RoomPanel extends View {
    /**
     * The room being displayed
     */
    private Room viewedRoom;
    /**
     * Gets the room being displayed
     * @return The room being displayed
     */
    public Room getRoom() {
        return viewedRoom;
    }

    // the center
    private BorderLayout layout = new BorderLayout();
    private JPanel roomInfo = new JPanel();
    private JPanel actorList = new JPanel();

    private ArrayList<ActorPanel> students = new ArrayList<>();
    private ArrayList<ActorPanel> professors = new ArrayList<>();
    private ArrayList<ActorPanel> janitors = new ArrayList<>();

    private JScrollPane[] scrollLists = new JScrollPane[4];
    private DoorListPanel[] doorLists = new DoorListPanel[4];

    // content in the center
    private InventoryPanel inventoryPanel;
    /**
     * Creates a new room panel
     * @param room The room to display
     */
    public RoomPanel(Room room) {
        this.viewedRoom = room;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inventoryPanel = room.getInventory().createOwnView();

        for (int i = 0; i < 4; i++) {
            scrollLists[i] = createDoorScrollPane(i);
        }
        //Create door panels
        for (Door door : room.getDoors()) {
            Room other = door.leadsTo(room);
            //Do not create door if it is one-way in the other direction, or it is invisible
            if (other == null || door.getRemainingInvisibility() > 0) continue;
            DoorPanel doorPanel = door.createOwnView();
            //Determine which side of the room the door is on
            //It uses XOR, the door is always on the opposite side when viewed from the other room
            //Using XOR makes it so the doors are placed on up/down or left/right sides more evenly
            int idXor = room.getId() ^ other.getId();
            int c = 0;
            while (idXor > 0) {
                c += idXor & 1;
                idXor >>= 1;
            }
            int side = c % 4;

            if (room.getId() < other.getId()){
                side = (side + 2) % 4;
            }

            doorLists[side].addDoor(doorPanel);
            doorPanel.bindRoom(room);
        }

        // add door lists
        add(scrollLists[0], BorderLayout.NORTH);
        add(scrollLists[1], BorderLayout.EAST);
        add(scrollLists[2], BorderLayout.SOUTH);
        add(scrollLists[3], BorderLayout.WEST);

        // init
        roomInfo.setLayout(layout);
        add(roomInfo, BorderLayout.CENTER);

        // setup actor list panel
        actorList.setLayout(new BoxLayout(actorList, BoxLayout.Y_AXIS));

        // add actors
        ItemHolderViewFactory.setAutoPlacement(this);
        room.getActors().forEach(actor -> {
            actor.createOwnView();
        });

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(85, 500));
        leftPanel.setLayout(new GridLayout(5, 1));

        roomInfo.add(leftPanel, BorderLayout.WEST);

        // Student button
        leftPanel.add(new CustomButton(
            IconLoader.getInstance().getIcon("student.png", 45), 
            " " + students.size(), 
            (e) -> {
                setSelectedButton(leftPanel, 0);
                setDisplayedActors("Students", students);
            }
        ).removeBorder());

        // Professor button
        leftPanel.add(new CustomButton(
            IconLoader.getInstance().getIcon("professor.png", 45), 
            " " + professors.size(),
            (e) -> {
                setSelectedButton(leftPanel, 1);
                setDisplayedActors("Professors", professors);
            }
        ).removeBorder());

        // Janitor button
        leftPanel.add(new CustomButton(
            IconLoader.getInstance().getIcon("cleaner.png", 45), 
            " " + janitors.size(),
            (e) -> {
                setSelectedButton(leftPanel, 2);
                setDisplayedActors("Janitors", janitors);
            }
        ).removeBorder());
        
        // Empty space
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(ThemeManager.BACKGROUND);
        leftPanel.add(emptyPanel);

        // Inventory button
        leftPanel.add(new CustomButton(IconLoader.getInstance().getIcon("chest.png", 45), (e) -> {
            setSelectedButton(leftPanel, 4);
            ReplaceCenter("Room Inventory", inventoryPanel);
        }).removeBorder());

        // by default, show inventory
        ReplaceCenter("Room Inventory", inventoryPanel);
        setSelectedButton(leftPanel, 4);

        revalidate();
        repaint();
    }
    /**
     * Creates a scroll pane for the doors
     * @param i The index of the scroll pane
     * @return The created scroll pane
     */
    private JScrollPane createDoorScrollPane(int i) {
        doorLists[i] = new DoorListPanel(i % 2 == 0 ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS);
        
        JScrollPane scrollPane = new JScrollPane(doorLists[i]);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollPane.getVerticalScrollBar().setUI(new ScrollUI());
        scrollPane.getHorizontalScrollBar().setUI(new ScrollUI());

        scrollPane.setPreferredSize(i % 2 == 0 ? new Dimension(300, 120) : new Dimension(120, 300));
        scrollPane.setBorder(i % 2 == 0 ? BorderFactory.createEmptyBorder(0, 12, 0, 12) : null);
        scrollPane.setBackground(ThemeManager.BUTTON);
        return scrollPane;
    }
    /**
     * Adds a student view to the room panel 
     * @param studentPanel The student panel to add
     */
    public void AddStudentView(ActorPanel studentPanel) {
        students.add(studentPanel);
    }
    /**
     * Adds a professor view to the room panel
     * @param professorPanel The professor panel to add
     */
    public void AddProfessorView(ActorPanel professorPanel) {
        professors.add(professorPanel);
    }
    /**
     * Adds a janitor view to the room panel
     * @param janitorPanel The janitor panel to add
     */
    public void AddJanitorView(ActorPanel janitorPanel) {
        janitors.add(janitorPanel);
    }
    /**
     * Replaces the center of the panel with the given title and list of actor panels
     * @param title The title of the panel
     * @param actors The actors to display
     */
    private void setDisplayedActors(String title, ArrayList<ActorPanel> actors) {
        actorList.removeAll();

        actors.forEach(actor -> {
            actorList.add(actor);
        });

        ReplaceCenter(title, actorList);
    }
    /**
     * Replaces the center of the panel with the given title and panel
     * @param title The title of the panel
     * @param panel The panel to display
     */
    private void ReplaceCenter(String title, JPanel panel) {
        Component center = layout.getLayoutComponent(BorderLayout.CENTER);

        if (center != null) {
            roomInfo.remove(center);
        }

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());

        JLabel titleLablel = new JLabel(title, JLabel.CENTER);
        titleLablel.setFont(ThemeManager.getInstance().getFont(Font.BOLD, 20));
        titleLablel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        container.add(titleLablel, BorderLayout.NORTH);
        container.add(panel, BorderLayout.CENTER);

        roomInfo.add(container, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
    /**
     * Sets the selected button in the given holder
     * @param holder The holder of the buttons
     * @param index The index of the selected button
     */
    private void setSelectedButton(JPanel holder, int index) {
        for (int i = 0; i < holder.getComponentCount(); i++) {
            Component component = holder.getComponent(i);
            
            if(index == i) {
                component.setBackground(ThemeManager.BACKGROUND_DARK);
            } else {
                component.setBackground(ThemeManager.BACKGROUND);
            }
        }
    }

    @Override
    public void updateView() {
        // NOOP - the room does not change
    }

    @Override
    public View removeView() {
        viewedRoom.removeListener(this);
        inventoryPanel.removeView();
        return this;
    }
}
