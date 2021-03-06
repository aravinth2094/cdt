/*******************************************************************************
 * Copyright (c) 2017 Red Hat Inc and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Red Hat Inc. - Initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.managedbuilder.core;

import org.eclipse.cdt.managedbuilder.buildproperties.IOptionalBuildProperties;

/**
 * @since 8.6
 */
public interface IOptionalBuildObjectPropertiesContainer {
	IOptionalBuildProperties getOptionalBuildProperties();

}
