/*******************************************************************************
 * Copyright (c) 2015, 2016 Institute for Software, HSR Hochschule fuer Technik
 * Rapperswil, University of applied sciences.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Lukas Wegmann (IFS) - Initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.internal.core.pdom.dom.cpp;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.ast.ISemanticProblem;
import org.eclipse.cdt.core.dom.ast.IType;
import org.eclipse.cdt.core.dom.ast.IValue;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPTemplateArgument;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPTemplateDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPVariableInstance;
import org.eclipse.cdt.internal.core.dom.parser.ProblemType;
import org.eclipse.cdt.internal.core.dom.parser.Value;
import org.eclipse.cdt.internal.core.dom.parser.cpp.semantics.CPPTemplates;
import org.eclipse.cdt.internal.core.index.IIndexCPPBindingConstants;
import org.eclipse.cdt.internal.core.pdom.db.Database;
import org.eclipse.cdt.internal.core.pdom.dom.IPDOMBinding;
import org.eclipse.cdt.internal.core.pdom.dom.PDOMLinkage;
import org.eclipse.cdt.internal.core.pdom.dom.PDOMNode;
import org.eclipse.cdt.internal.core.pdom.dom.c.PDOMCAnnotation;
import org.eclipse.core.runtime.CoreException;

public class PDOMCPPVariableInstance extends PDOMCPPSpecialization implements ICPPVariableInstance {
	private static final int TEMPLATE_ARGUMENTS = PDOMCPPSpecialization.RECORD_SIZE + 0;
	private static final int TYPE = TEMPLATE_ARGUMENTS + Database.PTR_SIZE;
	private static final int VALUE = TYPE + Database.TYPE_SIZE;
	protected static final int ANNOTATIONS = VALUE + Database.VALUE_SIZE;

	@SuppressWarnings("hiding")
	protected static final int RECORD_SIZE = ANNOTATIONS + 1;

	private IType type;

	public PDOMCPPVariableInstance(PDOMCPPLinkage linkage, PDOMNode parent,
			ICPPVariableInstance specialization, IPDOMBinding orig) throws CoreException {
		super(linkage, parent, specialization, orig);

		final long argListRec = PDOMCPPArgumentList.putArguments(this, specialization.getTemplateArguments());
		final Database db = getDB();
		db.putRecPtr(record + TEMPLATE_ARGUMENTS, argListRec);
		getLinkage().storeType(record + TYPE, specialization.getType());
		getLinkage().storeValue(record + VALUE, specialization.getInitialValue());
		db.putByte(record + ANNOTATIONS, PDOMCPPAnnotation.encodeAnnotation(specialization));
	}

	public PDOMCPPVariableInstance(PDOMLinkage linkage, long bindingRecord) {
		super(linkage, bindingRecord);
	}

	@Override
	public ICPPTemplateDefinition getTemplateDefinition() {
		return (ICPPTemplateDefinition) getSpecializedBinding();
	}

	@Override
	public ICPPTemplateArgument[] getTemplateArguments() {
		try {
			final long rec = getPDOM().getDB().getRecPtr(record + TEMPLATE_ARGUMENTS);
			return PDOMCPPArgumentList.getArguments(this, rec);
		} catch (CoreException e) {
			CCorePlugin.log(e);
			return ICPPTemplateArgument.EMPTY_ARGUMENTS;
		}
	}

	@Override
	public boolean isExplicitSpecialization() {
		try {
			return hasDeclaration();
		} catch (CoreException e) {
			return false;
		}
	}

	@Override
	@Deprecated
	public IType[] getArguments() {
		return CPPTemplates.getArguments(getTemplateArguments());
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public boolean isAuto() {
		return getBit(getByte(record + ANNOTATIONS), PDOMCAnnotation.AUTO_OFFSET);
	}

	@Override
	public boolean isExtern() {
		return getBit(getByte(record + ANNOTATIONS), PDOMCAnnotation.EXTERN_OFFSET);
	}

	@Override
	public boolean isExternC() {
		return getBit(getByte(record + ANNOTATIONS), PDOMCPPAnnotation.EXTERN_C_OFFSET);
	}

	@Override
	public boolean isRegister() {
		return getBit(getByte(record + ANNOTATIONS), PDOMCAnnotation.REGISTER_OFFSET);
	}

	@Override
	public boolean isStatic() {
		return getBit(getByte(record + ANNOTATIONS), PDOMCAnnotation.STATIC_OFFSET);
	}

	@Override
	public IType getType() {
		if (type == null) {
			try {
				type = getLinkage().loadType(record + TYPE);
			} catch (CoreException e) {
				CCorePlugin.log(e);
				type = new ProblemType(ISemanticProblem.TYPE_NOT_PERSISTED);
			}
		}
		return type;
	}

	@Override
	public IValue getInitialValue() {
		try {
			return getLinkage().loadValue(record + VALUE);
		} catch (CoreException e) {
			CCorePlugin.log(e);
			return Value.UNKNOWN;
		}
	}

	@Override
	protected int getRecordSize() {
		return RECORD_SIZE;
	}

	@Override
	public int getNodeType() {
		return IIndexCPPBindingConstants.CPP_VARIABLE_INSTANCE;
	}
}