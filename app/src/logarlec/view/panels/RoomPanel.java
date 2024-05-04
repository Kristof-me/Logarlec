package logarlec.view.panels;

import javax.swing.*;

import logarlec.model.room.Room;
import logarlec.view.observerviews.View;

import java.awt.*;

public class RoomPanel extends View{
    Room viewedRoom;

    public RoomPanel(Room room) {
        this.viewedRoom = room;
        
    }

    @Override
    public void updateView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateView'");
    }
}
