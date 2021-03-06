/*******************************************************************************
 * Copyright (c) 2011, 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.app.console.shared.model;

import java.io.Serializable;
import java.util.Set;

import org.eclipse.kapua.app.console.shared.model.account.GwtAccount;
import org.eclipse.kapua.app.console.shared.model.user.GwtUser;

public class GwtSession extends KapuaBaseModel implements Serializable {

    private static final long serialVersionUID = -4511854889803351914L;

    // Console info
    private String version;
    private String buildVersion;
    private String buildNumber;

    // User info
    private GwtUser user;
    private GwtAccount gwtAccount;
    private GwtAccount rootAccount;
    private GwtAccount selectedAccount;
    private Set<String> permissions;

    // Static loaded permission
    private boolean hasAccountCreatePermission;
    private boolean hasAccountReadPermission;
    private boolean hasAccountUpdatePermission;
    private boolean hasAccountDeletePermission;
    private boolean hasAccountAllPermission;

    private boolean hasDeviceCreatePermission;
    private boolean hasDeviceReadPermission;
    private boolean hasDeviceUpdatePermission;
    private boolean hasDeviceDeletePermission;
    private boolean hasDeviceManagePermission;

    private boolean hasDataReadPermission;

    private boolean hasTagCreatePermission;
    private boolean hasTagReadPermission;
    private boolean hasTagUpdatePermission;
    private boolean hasTagDeletePermission;

    private boolean hasUserCreatePermission;
    private boolean hasUserReadPermission;
    private boolean hasUserUpdatePermission;
    private boolean hasUserDeletePermission;

    private boolean hasRoleCreatePermission;
    private boolean hasRoleReadPermission;
    private boolean hasRoleUpdatePermission;
    private boolean hasRoleDeletePermission;

    private boolean hasGroupCreatePermission;
    private boolean hasGroupReadPermission;
    private boolean hasGroupUpdatePermission;
    private boolean hasGroupDeletePermission;

    private boolean hasCredentialCreatePermission;
    private boolean hasCredentialReadPermission;
    private boolean hasCredentialUpdatePermission;
    private boolean hasCredentialDeletePermission;

    private boolean hasConnectionCreatePermission;
    private boolean hasConnectionReadPermission;
    private boolean hasConnectionUpdatePermission;
    private boolean hasConnectionDeletePermission;

    public GwtSession() {
    }

    public GwtUser getUser() {
        return user;
    }

    public void setGwtUser(GwtUser gwtUser) {
        this.user = gwtUser;
    }

    public GwtAccount getGwtAccount() {
        return gwtAccount;
    }

    public void setGwtAccount(GwtAccount gwtAccount) {
        this.gwtAccount = gwtAccount;
    }

    public void setRootAccount(GwtAccount rootAccount) {
        this.rootAccount = rootAccount;
    }

    public GwtAccount getRootAccount() {
        return rootAccount;
    }

    public void setSelectedAccount(GwtAccount selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public GwtAccount getSelectedAccount() {
        return selectedAccount;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public boolean hasAccountCreatePermission() {
        return hasAccountCreatePermission;
    }

    public void setAccountCreatePermission(boolean value) {
        this.hasAccountCreatePermission = value;
    }

    public boolean hasAccountReadPermission() {
        return hasAccountReadPermission;
    }

    public void setAccountReadPermission(boolean value) {
        this.hasAccountReadPermission = value;
    }

    public boolean hasAccountUpdatePermission() {
        return hasAccountUpdatePermission;
    }

    public void setAccountUpdatePermission(boolean value) {
        this.hasAccountUpdatePermission = value;
    }

    public boolean hasAccountDeletePermission() {
        return hasAccountDeletePermission;
    }

    public void setAccountDeletePermission(boolean value) {
        this.hasAccountDeletePermission = value;
    }

    public boolean hasAccountAllPermission() {
        return hasAccountAllPermission;
    }

    public void setAccountAllPermission(boolean hasAccountAll) {
        this.hasAccountAllPermission = hasAccountAll;
    }

    public boolean hasDataReadPermission() {
        return hasDataReadPermission;
    }

    public void setDataReadPermission(boolean value) {
        this.hasDataReadPermission = value;
    }

    public boolean hasTagReadPermission() {
        return hasTagReadPermission;
    }

    public void setTagReadPermission(boolean value) {
        this.hasTagReadPermission = value;
    }

    public boolean hasTagCreatePermission() {
        return hasTagCreatePermission;
    }

    public void setTagCreatePermission(boolean hasTagCreatePermission) {
        this.hasTagCreatePermission = hasTagCreatePermission;
    }

    public boolean hasTagUpdatePermission() {
        return hasTagUpdatePermission;
    }

    public void setTagUpdatePermission(boolean hasTagUpdatePermission) {
        this.hasTagUpdatePermission = hasTagUpdatePermission;
    }

    public boolean hasTagDeletePermission() {
        return hasTagDeletePermission;
    }

    public void setTagDeletePermission(boolean hasTagDeletePermission) {
        this.hasTagDeletePermission = hasTagDeletePermission;
    }

    public boolean hasUserReadPermission() {
        return hasUserReadPermission;
    }

    public void setUserReadPermission(boolean value) {
        this.hasUserReadPermission = value;
    }

    public boolean hasUserCreatePermission() {
        return hasUserCreatePermission;
    }

    public void setUserCreatePermission(boolean hasUserCreatePermission) {
        this.hasUserCreatePermission = hasUserCreatePermission;
    }

    public boolean hasUserUpdatePermission() {
        return hasUserUpdatePermission;
    }

    public void setUserUpdatePermission(boolean hasUserUpdatePermission) {
        this.hasUserUpdatePermission = hasUserUpdatePermission;
    }

    public boolean hasUserDeletePermission() {
        return hasUserDeletePermission;
    }

    public void setUserDeletePermission(boolean hasUserDeletePermission) {
        this.hasUserDeletePermission = hasUserDeletePermission;
    }

    public boolean hasRoleReadPermission() {
        return hasRoleReadPermission;
    }

    public void setRoleReadPermission(boolean value) {
        this.hasRoleReadPermission = value;
    }

    public boolean hasRoleCreatePermission() {
        return hasRoleCreatePermission;
    }

    public void setRoleCreatePermission(boolean hasRoleCreatePermission) {
        this.hasRoleCreatePermission = hasRoleCreatePermission;
    }

    public boolean hasRoleUpdatePermission() {
        return hasRoleUpdatePermission;
    }

    public void setRoleUpdatePermission(boolean hasRoleUpdatePermission) {
        this.hasRoleUpdatePermission = hasRoleUpdatePermission;
    }

    public boolean hasRoleDeletePermission() {
        return hasRoleDeletePermission;
    }

    public void setRoleDeletePermission(boolean hasRoleDeletePermission) {
        this.hasRoleDeletePermission = hasRoleDeletePermission;
    }

    public boolean hasGroupReadPermission() {
        return hasGroupReadPermission;
    }

    public void setGroupReadPermission(boolean value) {
        this.hasGroupReadPermission = value;
    }

    public boolean hasGroupCreatePermission() {
        return hasGroupCreatePermission;
    }

    public void setGroupCreatePermission(boolean hasGroupCreatePermission) {
        this.hasGroupCreatePermission = hasGroupCreatePermission;
    }

    public boolean hasGroupUpdatePermission() {
        return hasGroupUpdatePermission;
    }

    public void setGroupUpdatePermission(boolean hasGroupUpdatePermission) {
        this.hasGroupUpdatePermission = hasGroupUpdatePermission;
    }

    public boolean hasGroupDeletePermission() {
        return hasGroupDeletePermission;
    }

    public void setGroupDeletePermission(boolean hasGroupDeletePermission) {
        this.hasGroupDeletePermission = hasGroupDeletePermission;
    }

    public boolean hasDeviceCreatePermission() {
        return hasDeviceCreatePermission;
    }

    public void setDeviceCreatePermission(boolean hasDeviceCreatePermission) {
        this.hasDeviceCreatePermission = hasDeviceCreatePermission;
    }

    public boolean hasDeviceReadPermission() {
        return hasDeviceReadPermission;
    }

    public void setDeviceReadPermission(boolean hasDeviceReadPermission) {
        this.hasDeviceReadPermission = hasDeviceReadPermission;
    }

    public boolean hasDeviceUpdatePermission() {
        return hasDeviceUpdatePermission;
    }

    public void setDeviceUpdatePermission(boolean hasDeviceUpdatePermission) {
        this.hasDeviceUpdatePermission = hasDeviceUpdatePermission;
    }

    public boolean hasDeviceDeletePermission() {
        return hasDeviceDeletePermission;
    }

    public void setDeviceDeletePermission(boolean hasDeviceDeletePermission) {
        this.hasDeviceDeletePermission = hasDeviceDeletePermission;
    }

    public boolean hasDeviceManagePermission() {
        return hasDeviceManagePermission;
    }

    public void setDeviceManagePermission(boolean hasDeviceManagePermission) {
        this.hasDeviceManagePermission = hasDeviceManagePermission;
    }

    public boolean hasCredentialCreatePermission() {
        return hasCredentialCreatePermission;
    }

    public void setCredentialCreatePermission(boolean hasCredentialCreatePermission) {
        this.hasCredentialCreatePermission = hasCredentialCreatePermission;
    }

    public boolean hasCredentialReadPermission() {
        return hasCredentialReadPermission;
    }

    public void setCredentialReadPermission(boolean hasCredentialReadPermission) {
        this.hasCredentialReadPermission = hasCredentialReadPermission;
    }

    public boolean hasCredentialUpdatePermission() {
        return hasCredentialUpdatePermission;
    }

    public void setCredentialUpdatePermission(boolean hasCredentialUpdatePermission) {
        this.hasCredentialUpdatePermission = hasCredentialUpdatePermission;
    }

    public boolean hasCredentialDeletePermission() {
        return hasCredentialDeletePermission;
    }

    public void setCredentialDeletePermission(boolean hasCredentialDeletePermission) {
        this.hasCredentialDeletePermission = hasCredentialDeletePermission;
    }

    public boolean hasConnectionCreatePermission() {
        return hasConnectionCreatePermission;
    }

    public void setConnectionCreatePermission(boolean hasConnectionCreatePermission) {
        this.hasConnectionCreatePermission = hasConnectionCreatePermission;
    }

    public boolean hasConnectionReadPermission() {
        return hasConnectionReadPermission;
    }

    public void setConnectionReadPermission(boolean hasConnectionReadPermission) {
        this.hasConnectionReadPermission = hasConnectionReadPermission;
    }

    public boolean hasConnectionUpdatePermission() {
        return hasConnectionUpdatePermission;
    }

    public void setConnectionUpdatePermission(boolean hasConnectionUpdatePermission) {
        this.hasConnectionUpdatePermission = hasConnectionUpdatePermission;
    }

    public boolean hasConnectionDeletePermission() {
        return hasConnectionDeletePermission;
    }

    public void setConnectionDeletePermission(boolean hasConnectionDeletePermission) {
        this.hasConnectionDeletePermission = hasConnectionDeletePermission;
    }
}
