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
package com.jcabi.github.mock;

import com.jcabi.github.Release;
import com.jcabi.github.Releases;
import com.jcabi.github.Repo;
import javax.json.Json;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test case for {@link MkReleases}.
 * @author Paul Polishchuk (ppol@ua.fm)
 * @version $Id$
 * @since 0.8
 */
public final class MkReleasesTest {
    /**
     * MkReleases can fetch empty list of releases.
     * @throws Exception if some problem inside
     */
    @Test
    public void canFetchEmptyListOfReleases() throws Exception {
        final Releases releases = MkReleasesTest.repo().releases();
        MatcherAssert.assertThat(
            releases.iterate(),
            Matchers.emptyIterable()
        );
    }

    /**
     * MkReleases can fetch non-empty list of releases.
     * @todo #181 MkReleases should be able to fetch non-empty list of releases.
     *  MkReleases.create() method is necessary for this test, so the test
     *  should be implemented after the create() method. See
     *  http://developer.github.com/v3/repos/releases/#create-a-release.
     *  Let's implement this test and MkReleases.iterate(). When done, remove
     *  this puzzle and Ignore annotation from this method.
     */
    @Test
    @Ignore
    public void canFetchNonEmptyListOfReleases() {
        // To be implemented
    }

    /**
     * MkReleases can fetch a single release.
     * @throws Exception If some problem inside
     */
    @Test
    public void canFetchSingleRelease() throws Exception {
        final Releases releases = MkReleasesTest.repo().releases();
        MatcherAssert.assertThat(releases.get(1), Matchers.notNullValue());
    }

    /**
     * MkReleases can create a release.
     * @throws Exception If some problem inside
     */
    @Test
    public void canCreateRelease() throws Exception {
        final Releases releases = MkReleasesTest.repo().releases();
        final String tag = "v1.0.0";
        final Release release = releases.create(tag);
        MatcherAssert.assertThat(
            release.json().getString("tag_name"),
            Matchers.equalTo(tag)
        );
    }

    /**
     * Create a repo to work with.
     * @return Repo
     * @throws Exception If some problem inside
     */
    private static Repo repo() throws Exception {
        return new MkGithub().repos().create(
            Json.createObjectBuilder().add("name", "test").build()
        );
    }
}
