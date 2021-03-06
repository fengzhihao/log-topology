package storm.cookbook.log;

import java.io.IOException;

import org.jmock.Expectations;
import org.junit.Test;

import storm.cookbook.log.model.LogEntry;
import backtype.storm.task.OutputCollector;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class TestRulesBolt extends StormTestCase {

	@Test
	public void test() throws IOException {
        final OutputCollector collector = context.mock(OutputCollector.class);
        final LogEntry entry = getEntry();
		entry.setHost("localhost");
        final Tuple tuple = getTuple();
        final LogEntry testEntry = getEntry();
        testEntry.setHost("localhost.example.com");
        LogRulesBolt bolt = new LogRulesBolt();
        bolt.prepare(null, null, collector);

        context.checking(new Expectations(){{
        	oneOf(tuple).getValueByField(FieldNames.LOG_ENTRY);
        	will(returnValue(entry));
        	oneOf(collector).emit(new Values(testEntry));
        }});

        bolt.execute(tuple);
        context.assertIsSatisfied();

	}

}
