/*******************************************************************************
 * Copyright (c) 2007 Wind River Systems, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Markus Schorn - initial API and implementation
 *******************************************************************************/

package org.eclipse.cdt.ui.dialogs;

import org.eclipse.osgi.util.NLS;

public class DialogsMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.cdt.ui.dialogs.DialogsMessages"; //$NON-NLS-1$
	public static String AbstractIndexerPage_indexAllFiles;
	public static String AbstractIndexerPage_indexUpFront;
	public static String AbstractIndexerPage_skipAllReferences;
	public static String AbstractIndexerPage_skipTypeReferences;
	public static String IndexerStrategyBlock_activeBuildConfig;
	public static String IndexerStrategyBlock_autoUpdate;
	public static String IndexerStrategyBlock_buildConfigGroup;
	public static String IndexerStrategyBlock_immediateUpdate;
	public static String IndexerStrategyBlock_specificBuildConfig;
	public static String IndexerStrategyBlock_strategyGroup;
	public static String PreferenceScopeBlock_enableProjectSettings;
	public static String PreferenceScopeBlock_preferenceLink;
	public static String PreferenceScopeBlock_storeWithProject;
	public static String CacheSizeBlock_absoluteLimit;
	public static String CacheSizeBlock_cacheLimitGroup;
	public static String CacheSizeBlock_headerFileCache;
	public static String CacheSizeBlock_indexDatabaseCache;
	public static String CacheSizeBlock_limitRelativeToMaxHeapSize;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, DialogsMessages.class);
	}

	private DialogsMessages() {
	}
}
