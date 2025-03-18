package jroullet.msnotes.dto;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class NoteId {
    private ObjectId id;
}
