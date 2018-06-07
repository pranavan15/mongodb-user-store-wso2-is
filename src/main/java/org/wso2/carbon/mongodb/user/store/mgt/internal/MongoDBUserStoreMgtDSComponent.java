/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.mongodb.user.store.mgt.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.mongodb.user.store.mgt.MongoDBUserStoreManager;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.carbon.user.core.tracker.UserStoreManagerRegistry;
import org.wso2.carbon.user.core.util.DatabaseUtil;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;

/**
 * @scr.component name="mongodb.user.store.mgt.dscomponent" immediate=true
 * @scr.reference name="user.realmservice.default"
 * interface="org.wso2.carbon.user.core.service.RealmService" cardinality="1..1"
 * policy="dynamic" bind="setRealmService"
 * unbind="unsetRealmService"
 */
@SuppressWarnings({"unused", "JavaDoc"})
public class MongoDBUserStoreMgtDSComponent {

    private static final Log log = LogFactory.getLog(MongoDBUserStoreMgtDSComponent.class);
    private static RealmService realmService;

    protected void activate(ComponentContext ctxt) {

        try {
            // We assume this component gets activated by super tenant
            PrivilegedCarbonContext carbonContext = PrivilegedCarbonContext.getThreadLocalCarbonContext();
            carbonContext.setTenantId(MultitenantConstants.SUPER_TENANT_ID);
            carbonContext.setTenantDomain(MultitenantConstants.SUPER_TENANT_DOMAIN_NAME);

            UserStoreManager mongoUserStoreManager = new MongoDBUserStoreManager();
            ctxt.getBundleContext().registerService(UserStoreManager.class.getName(), mongoUserStoreManager, null);

            UserStoreManagerRegistry.init(ctxt.getBundleContext());

            MongoDBUserStoreManager.setDBDataSource(
                    DatabaseUtil.getRealmDataSource(realmService.getBootstrapRealmConfiguration())
            );
            log.info("MongoDBUserStoreMgtDSComponent activated successfully.");
        } catch (Exception e) {
            log.error("Failed to activate Carbon UserStoreMgtDSComponent ", e);
        }
    }

    protected void setRealmService(RealmService rlmService) {
        if (log.isDebugEnabled()) {
            log.debug("Set the realmService");
        }
        MongoDBUserStoreMgtDSComponent.realmService = rlmService;
    }

    protected void unsetRealmService(RealmService rlmService) {
        if (log.isDebugEnabled()) {
            log.debug("Unset the realmService");
        }
        MongoDBUserStoreMgtDSComponent.realmService = null;
    }

}
