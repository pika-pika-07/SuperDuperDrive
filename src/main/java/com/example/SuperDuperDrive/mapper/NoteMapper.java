package com.example.SuperDuperDrive.mapper;

import com.example.SuperDuperDrive.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getAllNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)"
            + " VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription}"
            + " WHERE noteid = #{noteid} AND userid = #{userid}")
    Integer update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid}")
    Integer delete(Integer noteid, Integer userid);
}
