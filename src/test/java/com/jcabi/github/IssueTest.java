/**
 * Copyright (c) 2012-2013, JCabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.github;

import javax.json.Json;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test case for {@link Issue}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @checkstyle MultipleStringLiterals (500 lines)
 */
public final class IssueTest {

    /**
     * Issue.Smart can fetch key properties of an Issue.
     * @throws Exception If some problem inside
     */
    @Test
    public void fetchesProperties() throws Exception {
        final Issue issue = Mockito.mock(Issue.class);
        Mockito.doReturn(
            Json.createObjectBuilder()
                .add("title", "this is some text \u20ac")
                .add("body", "body of the issue")
                .build()
        ).when(issue).json();
        final Issue.Smart smart = new Issue.Smart(issue);
        MatcherAssert.assertThat(
            smart.title(),
            Matchers.notNullValue()
        );
        MatcherAssert.assertThat(
            smart.body(),
            Matchers.notNullValue()
        );
    }

    /**
     * Issue.Smart can detect a pull request.
     * @throws Exception If some problem inside
     */
    @Test
    public void detectsPullRequest() throws Exception {
        final Issue issue = Mockito.mock(Issue.class);
        Mockito.doReturn(
            Json.createObjectBuilder().add(
                "pull_request",
                Json.createObjectBuilder().add(
                    "html_url", "http://ibm.com/pulls/1"
                )
            ).build()
        ).when(issue).json();
        final Pulls pulls = Mockito.mock(Pulls.class);
        final Repo repo = Mockito.mock(Repo.class);
        Mockito.doReturn(repo).when(issue).repo();
        Mockito.doReturn(pulls).when(repo).pulls();
        MatcherAssert.assertThat(
            new Issue.Smart(issue).isPull(),
            Matchers.is(true)
        );
        new Issue.Smart(issue).pull();
        Mockito.verify(pulls).get(1);
    }

    /**
     * Issue.Smart can detect an absence of a pull request.
     * @throws Exception If some problem inside
     */
    @Test
    public void detectsPullRequestAbsence() throws Exception {
        final Issue issue = Mockito.mock(Issue.class);
        Mockito.doReturn(
            Json.createObjectBuilder().add(
                "pull_request",
                Json.createObjectBuilder().addNull("html_url")
            ).build()
        ).when(issue).json();
        MatcherAssert.assertThat(
            new Issue.Smart(issue).isPull(),
            Matchers.is(false)
        );
    }

}