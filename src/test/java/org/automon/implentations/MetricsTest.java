package org.automon.implentations;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MetricsTest {
    private Metrics openMon = new Metrics();
    private static final String LABEL =  "helloWorld.timer";
    private static final String EXCEPTION = "org.automon.MyException";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testStart() throws Exception {
        Timer mon = openMon.start(LABEL);
        assertThat(mon.getCount()).describedAs("The timer shouldn't have completed").isEqualTo(0);
    }

    @Test
    public void testStop() throws Exception {
        Timer mon = openMon.start(LABEL);
        openMon.stop(mon);
        assertThat(mon.getCount()).describedAs("The timer should have completed/been stopped").isEqualTo(1);
    }

    @Test
    public void testException() throws Exception {
        MetricRegistry metricRegistry = openMon.getMetricRegistry();
        assertThat(metricRegistry.counter(EXCEPTION).getCount()).describedAs("No exception should exist yet").isEqualTo(0);
        openMon.exception(EXCEPTION);
        assertThat(metricRegistry.counter(EXCEPTION).getCount()).describedAs("An exception should now exist").isEqualTo(1);
    }

    @Test
    public void testShouldBeEnabledByDefault() throws Exception {
        assertThat(openMon.isEnabled()).describedAs("Should be enabled by default").isTrue();
    }

    @Test
    public void testEnableDisable() throws Exception {
        openMon.enable(false);
        assertThat(openMon.isEnabled()).describedAs("Metrics can't be enabled/disabled so it is a noop").isTrue();
      }

    @Test
    public void setMetricRegistry() throws Exception {
        MetricRegistry newMetricRegistry = new MetricRegistry();
        openMon.setMetricRegistry(newMetricRegistry);
        assertThat(openMon.getMetricRegistry()).isEqualTo(newMetricRegistry);
    }

}