package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MeetingBuilder;

public class MeetingTest {

    private final Meeting meeting = new MeetingBuilder()
            .withContactUids(List.of(UUID.randomUUID(), UUID.randomUUID())).build();

    @Test
    public void convertContactUidsToString_equals() {
        String expected = meeting.getContactUids().get(0).toString() + ","
                + meeting.getContactUids().get(1).toString();
        assertTrue(meeting.convertContactUidsToString().equals(expected));
    }

    @Test
    public void hasConflictMeeting_true() {
        // conflicts with itself
        assertTrue(meeting.hasConflictMeeting(meeting));
        // conflicts with a meeting with same everything
        Meeting otherMeeting = new MeetingBuilder(meeting).build();
        assertTrue(meeting.hasConflictMeeting(otherMeeting));
    }

    @Test
    public void hasConflictMeeting_false() {
        // no conflicts with null
        assertFalse(meeting.hasConflictMeeting(null));
        // no conflicts with a meeting with different date
        Meeting otherMeeting = new MeetingBuilder(meeting)
                .withDate(meeting.getMeetingDate().plusDays(1)).build();
        assertFalse(meeting.hasConflictMeeting(otherMeeting));
        // no conflicts with a meeting with different time
        otherMeeting = new MeetingBuilder(meeting)
                .withTime(meeting.getMeetingTime().plusHours(1)).build();
        assertTrue(meeting.getMeetingDate().equals(otherMeeting.getMeetingDate()));
        assertFalse(meeting.hasConflictMeeting(otherMeeting));
        // no conflicts with a meeting with different set of contact uids
        otherMeeting = new MeetingBuilder(meeting)
                .withContactUids(List.of(UUID.randomUUID())).build();
        assertTrue(meeting.getMeetingDate().equals(otherMeeting.getMeetingDate()));
        assertTrue(meeting.getMeetingTime().equals(otherMeeting.getMeetingTime()));
        assertFalse(meeting.hasConflictMeeting(otherMeeting));
    }

    @Test
    public void isSameMeeting_true() {
        // same object
        assertTrue(meeting.isSameMeeting(meeting));
        // same everything
        Meeting otherMeeting = new MeetingBuilder(meeting).build();
        assertTrue(meeting.isSameMeeting(otherMeeting));
    }

    @Test
    public void isSameMeeting_false() {
        // different meeting as null
        assertFalse(meeting.isSameMeeting(null));
        // different date
        Meeting otherMeeting = new MeetingBuilder(meeting)
                .withDate(meeting.getMeetingDate().plusDays(1)).build();
        assertFalse(meeting.isSameMeeting(otherMeeting));
        // different time
        otherMeeting = new MeetingBuilder(meeting)
                .withTime(meeting.getMeetingTime().plusHours(1)).build();
        assertTrue(meeting.getMeetingDate().equals(otherMeeting.getMeetingDate()));
        assertFalse(meeting.isSameMeeting(otherMeeting));
        // different name
        otherMeeting = new MeetingBuilder(meeting)
                .withName("Different Name").build();
        assertTrue(meeting.getMeetingDate().equals(otherMeeting.getMeetingDate()));
        assertTrue(meeting.getMeetingTime().equals(otherMeeting.getMeetingTime()));
        assertFalse(meeting.isSameMeeting(otherMeeting));
        // different set of contact uids
        otherMeeting = new MeetingBuilder(meeting)
                .withContactUids(List.of(UUID.randomUUID())).build();
        assertTrue(meeting.getMeetingDate().equals(otherMeeting.getMeetingDate()));
        assertTrue(meeting.getMeetingTime().equals(otherMeeting.getMeetingTime()));
        assertTrue(meeting.getMeetingName().equals(otherMeeting.getMeetingName()));
        assertFalse(meeting.isSameMeeting(otherMeeting));
    }

    @Test
    public void hasPerson_true() {
        assertTrue(meeting.hasPerson(meeting.getContactUids().get(0)));
        assertTrue(meeting.hasPerson(meeting.getContactUids().get(1)));
    }

    @Test
    public void hasPerson_false() {
        assertFalse(meeting.hasPerson(UUID.randomUUID()));
    }
}
