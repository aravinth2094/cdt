/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

/*
 * Created on Jan 24, 2005
 */
package org.eclipse.cdt.internal.core.dom.parser.cpp;

import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPDelegate;

/**
 * @author aniefer
 */
public interface ICPPInternalBinding extends IBinding {
    //methods required by the CPPVisitor but not meant for the public interface
    
    IASTNode [] getDeclarations();
    IASTNode getDefinition();
    
    ICPPDelegate createDelegate( IASTName name );
    
	/**
	 * @param declarator
	 */
	void addDefinition( IASTNode node );
	void addDeclaration( IASTNode node );
}
