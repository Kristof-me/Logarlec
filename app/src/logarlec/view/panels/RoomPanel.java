package logarlec.view.panels;

import javax.swing.*;

import logarlec.control.rendering.ItemHolderViewFactory;
import logarlec.model.room.Door;
import logarlec.model.room.Room;
import logarlec.view.elements.CustomButton;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;
import logarlec.view.elements.ScrollUI;
import java.util.ArrayList;

import java.awt.*;

public class RoomPanel extends View {
    private Room viewedRoom;

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

    public RoomPanel(Room room) {
        this.viewedRoom = room;
        this.setLayout(new BorderLayout());

        inventoryPanel = room.getInventory().createOwnView();

        for (int i = 0; i < 4; i++) {
            scrollLists[i] = createDoorScrollPane(i);
        }

        for (Door door : room.getDoors()) {
            Room other = door.leadsTo(room);
            //Do not create door if it is one-way in the other direction, or it is invisible
            if (other == null || door.getRemainingInvisibility() > 0) continue;
            DoorPanel doorPanel = door.createOwnView();

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
        leftPanel.setLayout(new GridLayout(4, 2));

        roomInfo.add(leftPanel, BorderLayout.WEST);

        // Student button
        leftPanel.add(new CustomButton(IconLoader.getInstance().getIcon("student.png", 45), (e) -> {
            setDisplayedActors(students);
        }));

        leftPanel.add(new JLabel(" " + students.size()));

        // Professor button
        leftPanel.add(new CustomButton(IconLoader.getInstance().getIcon("professor.png", 45), (e) -> {
            setDisplayedActors(professors);
        }));

        leftPanel.add(new JLabel(" " + professors.size()));

        // Janitor button
        leftPanel.add(new CustomButton(IconLoader.getInstance().getIcon("janitor.png", 45), (e) -> {
            setDisplayedActors(janitors);
        }));

        leftPanel.add(new JLabel(" " + janitors.size()));

        // Inventory button
        leftPanel.add(new CustomButton(IconLoader.getInstance().getIcon("chest.png", 45), (e) -> {
            ReplaceCenter(inventoryPanel);
        }));

        ReplaceCenter(inventoryPanel);
        revalidate();
        repaint();
    }

    private JScrollPane createDoorScrollPane(int i) {
        doorLists[i] = new DoorListPanel(i % 2 == 0 ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS);
        
        JScrollPane scrollPane = new JScrollPane(doorLists[i]);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollPane.getVerticalScrollBar().setUI(new ScrollUI());
        scrollPane.getHorizontalScrollBar().setUI(new ScrollUI());

        scrollPane.setPreferredSize(i % 2 == 0 ? new Dimension(300, 115) : new Dimension(115, 300));
        return scrollPane;
    }

    public void AddStudentView(ActorPanel studentPanel) {
        students.add(studentPanel);
    }

    public void AddProfessorView(ActorPanel professorPanel) {
        professors.add(professorPanel);
    }

    public void AddJanitorView(ActorPanel janitorPanel) {
        janitors.add(janitorPanel);
    }

    private void setDisplayedActors(ArrayList<ActorPanel> actors) {
        actorList.removeAll();

        actors.forEach(actor -> {
            actorList.add(actor);
        });

        ReplaceCenter(actorList);
    }

    private void ReplaceCenter(JPanel panel) {
        Component center = layout.getLayoutComponent(BorderLayout.CENTER);

        if (center != null) {
            roomInfo.remove(center);
        }

        roomInfo.add(panel, BorderLayout.CENTER);

        revalidate();
        repaint();
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
