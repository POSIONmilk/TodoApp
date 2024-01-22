import java.util.*;

public class Queue {

    public LinkedList<Task> task = new LinkedList<>();
//    public LinkedList<Object> title = new LinkedList<Object>();

    public void enqueueTask(Task eventAdded){
        task.addLast(eventAdded);
    };

    public void dequeueTask(){
        task.removeFirst();
    }

    public Task currTask(){
        return task.getFirst();
    }

    public boolean isEmpty(){
        return this.task.isEmpty();
    }

}
