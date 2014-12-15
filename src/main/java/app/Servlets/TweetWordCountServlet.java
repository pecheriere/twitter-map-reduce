package main.java.app.Servlets;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.mapreduce.*;
import com.google.appengine.tools.mapreduce.inputs.DatastoreInput;
import com.google.appengine.tools.mapreduce.outputs.DatastoreOutput;
import main.java.app.mapper.TweetWordCountMapper;
import main.java.app.reducer.TweetWordCountReducer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TweetWordCountServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 6681148191099787379L;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        MapReduceSettings settings = new MapReduceSettings.Builder()
                .setWorkerQueueName("default")
                .setBucketName("tweet-anal-cloud")
                .build();

        DatastoreInput input = new DatastoreInput("Tweet", 10);
        Mapper<Entity, String, Long> mapper = new TweetWordCountMapper();
        Marshaller<String> intermediateKeyMarshaller = Marshallers.getStringMarshaller();
        Marshaller<Long> intermediateValueMarshaller = Marshallers.getLongMarshaller();
        Reducer<String, Long, Entity> reducer = new TweetWordCountReducer();
        Output<Entity, Void> output = new DatastoreOutput();

        MapReduceSpecification<Entity, String, Long, Entity, Void>
                spec = new MapReduceSpecification.Builder<>(input, mapper, reducer, output)
                .setKeyMarshaller(intermediateKeyMarshaller)
                .setValueMarshaller(intermediateValueMarshaller)
                .setJobName("DemoMapreduce")
                .setNumReducers(10)
                .build();

        MapReduceJob.start(spec, settings);

    }
}
