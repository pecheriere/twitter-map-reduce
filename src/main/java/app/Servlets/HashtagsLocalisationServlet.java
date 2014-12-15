package main.java.app.Servlets;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.mapreduce.MapReduceJob;
import com.google.appengine.tools.mapreduce.MapReduceSettings;
import com.google.appengine.tools.mapreduce.MapReduceSpecification;
import com.google.appengine.tools.mapreduce.Mapper;
import com.google.appengine.tools.mapreduce.Marshaller;
import com.google.appengine.tools.mapreduce.Marshallers;
import com.google.appengine.tools.mapreduce.Output;
import com.google.appengine.tools.mapreduce.Reducer;
import com.google.appengine.tools.mapreduce.inputs.DatastoreInput;
import com.google.appengine.tools.mapreduce.outputs.DatastoreOutput;
import com.google.common.base.Pair;
import com.googlecode.objectify.ObjectifyService;

import main.java.app.mapper.HashtagsLocalisationMapper;
import main.java.app.models.Tweet;
import main.java.app.models.TweetCreator;
import main.java.app.reducer.HashtagsLocalisationReducer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HashtagsLocalisationServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 6681148191099787379L;

    static {
        ObjectifyService.register(Tweet.class);
        ObjectifyService.register(TweetCreator.class);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	MapReduceSettings settings = new MapReduceSettings.Builder()
        .setWorkerQueueName("default")
        .setBucketName("tweet-anal-cloud")
		.build();

        DatastoreInput input = new DatastoreInput("Tweet", 10);
        Mapper<Entity, Pair<String, String>, Pair<String, Long>> mapper = new HashtagsLocalisationMapper();
        Marshaller<Pair<String, String>> intermediateKeyMarshaller = Marshallers.getSerializationMarshaller();
        Marshaller<Pair<String, Long>> intermediateValueMarshaller = Marshallers.getSerializationMarshaller();
        Reducer<Pair<String, String>, Pair<String, Long>, Entity> reducer = new HashtagsLocalisationReducer();
        Output<Entity, Void> output = new DatastoreOutput();


        MapReduceSpecification<Entity, Pair<String, String>, Pair<String, Long>, Entity, Void>
                spec = new MapReduceSpecification.Builder<>(input, mapper, reducer, output)
                .setKeyMarshaller(intermediateKeyMarshaller)
                .setValueMarshaller(intermediateValueMarshaller)
                .setJobName("AssociatedHashtagMapReduce")
                .setNumReducers(10)
                .build();

        String id = MapReduceJob.start(spec, settings);
    }
}
