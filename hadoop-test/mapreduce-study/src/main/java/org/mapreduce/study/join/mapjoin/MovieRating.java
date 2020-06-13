package org.mapreduce.study.join.mapjoin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class MovieRating implements WritableComparable<MovieRating>{
    private String movieName;
    private int count;
    
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    
    public MovieRating() {}
    
    public MovieRating(String movieName, int count) {
        super();
        this.movieName = movieName;
        this.count = count;
    }
    
    
    @Override
    public String toString() {
        return  movieName + "\t" + count;
    }
    @Override
    public void readFields(DataInput in) throws IOException {
        movieName = in.readUTF();
        count = in.readInt();
    }
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(movieName);
        out.writeInt(count);
    }
    @Override
    public int compareTo(MovieRating o) {
        return o.count - this.count ;
    }
    
}
