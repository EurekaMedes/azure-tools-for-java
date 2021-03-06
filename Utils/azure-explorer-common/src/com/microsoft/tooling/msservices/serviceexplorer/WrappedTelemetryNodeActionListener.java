/**
 * Copyright (c) Microsoft Corporation
 * <p/>
 * All rights reserved.
 * <p/>
 * MIT License
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.microsoft.tooling.msservices.serviceexplorer;

import com.microsoft.azuretools.azurecommons.helpers.AzureCmdException;

public class WrappedTelemetryNodeActionListener extends NodeActionListener {

    private final NodeActionListener listener;
    private final String serviceName;
    private final String operationName;

    public WrappedTelemetryNodeActionListener(String serviceName, String operationName, NodeActionListener listener) {
        this.serviceName = serviceName;
        this.operationName = operationName;
        this.listener = listener;
    }

    @Override
    protected void actionPerformed(NodeActionEvent e) throws AzureCmdException {
        listener.actionPerformed(e);
    }

    @Override
    protected String getServiceName() {
        return this.serviceName;
    }

    @Override
    protected String getOperationName(NodeActionEvent e) {
        return this.operationName;
    }
}
