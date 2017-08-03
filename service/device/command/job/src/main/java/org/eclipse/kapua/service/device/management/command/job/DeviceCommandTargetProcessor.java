/*******************************************************************************
 * Copyright (c) 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.service.device.management.command.job;

import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;

import org.eclipse.kapua.commons.security.KapuaSecurityUtils;
import org.eclipse.kapua.locator.KapuaLocator;
import org.eclipse.kapua.service.device.management.command.DeviceCommandFactory;
import org.eclipse.kapua.service.device.management.command.DeviceCommandInput;
import org.eclipse.kapua.service.device.management.command.DeviceCommandManagementService;
import org.eclipse.kapua.service.job.context.KapuaJobContext;
import org.eclipse.kapua.service.job.context.KapuaStepContext;
import org.eclipse.kapua.service.job.operation.TargetOperation;
import org.eclipse.kapua.service.job.targets.JobTarget;
import org.eclipse.kapua.service.job.targets.JobTargetStatus;

public class DeviceCommandTargetProcessor implements TargetOperation {

    private final KapuaLocator locator = KapuaLocator.getInstance();
    private final DeviceCommandManagementService commandManagementService = locator.getService(DeviceCommandManagementService.class);
    private final DeviceCommandFactory commandFactory = locator.getFactory(DeviceCommandFactory.class);

    @Inject
    JobContext jobContext;

    @Inject
    StepContext stepContext;

    protected KapuaJobContext kapuaJobContext;
    protected KapuaStepContext kapuaStepContext;

    @Override
    public Object processItem(Object item) throws Exception {

        kapuaJobContext = (KapuaJobContext) jobContext.getTransientUserData();
        kapuaStepContext = (KapuaStepContext) stepContext.getTransientUserData();

        JobTarget jobTarget = (JobTarget) item;

        DeviceCommandInput commandInput = commandFactory.newCommandInput();
        commandInput.setCommand("ls");        // kapuaStepContext.getStepProperty(DeviceCommandExecPropertyKeys.COMMAND_INPUT);
        commandInput.setTimeout(30000);
        Long timeout = null; // kapuaStepContext.getStepProperty(DeviceCommandExecPropertyKeys.TIMEOUT);

        try {
            KapuaSecurityUtils.doPrivileged(() -> commandManagementService.exec(jobTarget.getScopeId(), jobTarget.getJobTargetId(), commandInput, timeout));

            jobTarget.setStatus(JobTargetStatus.PROCESS_OK);
            // jobTarget.setOutput(...);
        } catch (Exception e) {
            jobTarget.setStatus(JobTargetStatus.PROCESS_FAILED);
            jobTarget.setException(e);
        }

        return jobTarget;
    }

}