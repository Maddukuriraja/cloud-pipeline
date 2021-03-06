/*
 * Copyright 2017-2019 EPAM Systems, Inc. (https://www.epam.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.pipeline.manager.contextual.handler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.epam.pipeline.dao.user.UserDao;
import com.epam.pipeline.entity.contextual.ContextualPreference;
import com.epam.pipeline.entity.contextual.ContextualPreferenceLevel;
import com.epam.pipeline.entity.user.PipelineUser;
import org.junit.Test;

public class UserContextualPreferenceHandlerTest extends AbstractDaoContextualPreferenceHandlerTest {

    private final UserDao userDao = mock(UserDao.class);

    @Override
    public ContextualPreferenceHandler handler() {
        return new UserContextualPreferenceHandler(userDao, contextualPreferenceDao, nextHandler);
    }

    @Override
    public ContextualPreferenceHandler lastHandler() {
        return new UserContextualPreferenceHandler(userDao, contextualPreferenceDao);
    }

    @Override
    public ContextualPreferenceLevel level() {
        return ContextualPreferenceLevel.USER;
    }

    @Test
    public void isValidShouldReturnFalseIfUserDoesNotExist() {
        final ContextualPreference preference = new ContextualPreference(NAME, VALUE, resource);
        when(userDao.loadUserById(eq(Long.valueOf(RESOURCE_ID)))).thenReturn(null);

        assertFalse(handler().isValid(preference));
    }

    @Test
    public void isValidShouldReturnTrueIfUserExists() {
        final ContextualPreference preference = new ContextualPreference(NAME, VALUE, resource);
        when(userDao.loadUserById(eq(Long.valueOf(RESOURCE_ID)))).thenReturn(new PipelineUser());

        assertTrue(handler().isValid(preference));
    }
}
