/*
 * Copyright 2017 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.profiler.context.recorder;

import com.navercorp.pinpoint.bootstrap.context.SpanRecorder;
import com.navercorp.pinpoint.bootstrap.context.TraceId;
import com.navercorp.pinpoint.profiler.context.Span;
import com.navercorp.pinpoint.profiler.context.id.Shared;
import com.navercorp.pinpoint.profiler.context.id.TraceRoot;
import com.navercorp.pinpoint.profiler.metadata.SqlMetaDataService;
import com.navercorp.pinpoint.profiler.metadata.StringMetaDataService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Woonduk Kang(emeroad)
 */
@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class DefaultSpanRecorderTest {

    @Mock
    private TraceRoot traceRoot;
    @Mock
    private Shared shared;
    @Mock
    private TraceId traceId;
    @Mock
    private StringMetaDataService stringMetaDataService;
    @Mock
    private SqlMetaDataService sqlMetaDataService;

    @Before
    public void setUp() throws Exception {
        Mockito.when(traceRoot.getTraceId()).thenReturn(traceId);
    }

    @Test
    public void testRecordApiId() throws Exception {
        Span span = new Span(traceRoot);

        SpanRecorder recorder = new DefaultSpanRecorder(span, true, true, stringMetaDataService, sqlMetaDataService);

        final int API_ID = 1000;
        recorder.recordApiId(API_ID);

        Assert.assertEquals("API ID", span.getApiId(), API_ID);
    }

    @Test
    public void testRecordEndPoint() throws Exception {

        when(traceRoot.getShared()).thenReturn(shared);

        Span span = new Span(traceRoot);

        SpanRecorder recorder = new DefaultSpanRecorder(span, true, true, stringMetaDataService, sqlMetaDataService);

        final String endPoint = "endPoint";
        recorder.recordEndPoint(endPoint);

        Assert.assertEquals(span.getEndPoint(), endPoint);
        verify(traceRoot.getShared()).setEndPoint(endPoint);
    }

}