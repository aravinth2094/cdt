/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Common Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors: IBM - Initial API and implementation
 ******************************************************************************/
package org.eclipse.cdt.utils.xcoff.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.cdt.core.IBinaryParser;
import org.eclipse.cdt.core.IBinaryParser.IBinaryFile;
import org.eclipse.cdt.core.IBinaryParser.ISymbol;
import org.eclipse.cdt.utils.Addr2line;
import org.eclipse.cdt.utils.BinaryObjectAdapter;
import org.eclipse.cdt.utils.CPPFilt;
import org.eclipse.cdt.utils.Objdump;
import org.eclipse.cdt.utils.xcoff.XCoff32;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * TODO Provide description
 * 
 * @author vhirsl
 */
public class XCOFFBinaryObject extends BinaryObjectAdapter {
	Addr2line addr2line;
	BinaryObjectInfo info;
	ISymbol[] symbols;

	/**
	 * @param parser
	 * @param path
	 */
	public XCOFFBinaryObject(IBinaryParser parser, IPath path) {
		super(parser, path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.core.IBinaryParser.IBinaryObject#getSymbols()
	 */
	public ISymbol[] getSymbols() {
		if (hasChanged() || symbols == null) {
			try {
				loadAll();
			} catch (IOException e) {
				symbols = NO_SYMBOLS;
			}
		}
		return symbols;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.utils.BinaryObjectAdapter#getBinaryObjectInfo()
	 */
	protected BinaryObjectInfo getBinaryObjectInfo() {
		if (hasChanged() || info == null) {
			try {
				loadInfo();
			} catch (IOException e) {
				info = new BinaryObjectInfo();
			}
		}
		return info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.core.IBinaryParser.IBinaryFile#getType()
	 */
	public int getType() {
		return IBinaryFile.OBJECT;
	}
	
	/**
	 * @see org.eclipse.cdt.core.model.IBinaryParser.IBinaryFile#getContents()
	 */
	public InputStream getContents() {
		InputStream stream = null;
		Objdump objdump = getObjdump();
		if (objdump != null) {
			try {
				byte[] contents = objdump.getOutput();
				stream = new ByteArrayInputStream(contents);
			} catch (IOException e) {
				// Nothing
			}
		}
		if (stream == null) {
			stream = super.getContents();
		}
		return stream;
	}
	

	protected XCoff32 getXCoff32() throws IOException {
		return new XCoff32(getPath().toOSString());
	}

	protected void loadAll() throws IOException {
		XCoff32 xcoff = null;
		try {
			xcoff = getXCoff32();
			loadInfo(xcoff);
			loadSymbols(xcoff);
		} finally {
			if (xcoff != null) {
				xcoff.dispose();
			}
		}
	}

	protected void loadInfo() throws IOException {
		XCoff32 xcoff = null;
		try {
			xcoff = getXCoff32();
			loadInfo(xcoff);
		} finally {
			if (xcoff != null) {
				xcoff.dispose();
			}
		}
	}

	protected void loadInfo(XCoff32 xcoff) throws IOException {
		info = new BinaryObjectInfo();
		XCoff32.Attribute attribute = xcoff.getAttributes();
		info.isLittleEndian = attribute.isLittleEndian();
		info.hasDebug = attribute.hasDebug();
		info.cpu = attribute.getCPU();
	}

	protected void loadSymbols(XCoff32 xcoff) throws IOException {
		ArrayList list = new ArrayList();

		XCoff32.Symbol[] peSyms = xcoff.getSymbols();
		byte[] table = xcoff.getStringTable();
		addSymbols(peSyms, table, list);

		symbols = (ISymbol[]) list.toArray(NO_SYMBOLS);
		Arrays.sort(symbols);
		list.clear();
	}

	protected void addSymbols(XCoff32.Symbol[] peSyms, byte[] table, List list) {
		CPPFilt cppfilt = getCPPFilt();
		Addr2line addr2line = getAddr2line(false);
		for (int i = 0; i < peSyms.length; i++) {
			if (peSyms[i].isFunction() || peSyms[i].isVariable() ) {
				String name = peSyms[i].getName(table);
				if (name == null || name.trim().length() == 0 || !Character.isJavaIdentifierStart(name.charAt(0))) {
					continue;
				}
				int type = peSyms[i].isFunction() ? ISymbol.FUNCTION : ISymbol.VARIABLE;
				int addr = peSyms[i].n_value;
				int size = 4;
				if (cppfilt != null) {
					try {
						name = cppfilt.getFunction(name);
					} catch (IOException e1) {
						cppfilt = null;
					}
				}
				if (addr2line != null) {
					try {
						String filename = addr2line.getFileName(addr);
						// Addr2line returns the funny "??" when it can not find
						// the file.
						if (filename != null && filename.equals("??")) { //$NON-NLS-1$
							filename = null;
						}

						IPath file = filename != null ? new Path(filename) : Path.EMPTY;
						int startLine = addr2line.getLineNumber(addr);
						int endLine = addr2line.getLineNumber(addr + size - 1);
						list.add(new XCoffSymbol(this, name, type, addr, size, file, startLine, endLine));
					} catch (IOException e) {
						addr2line = null;
					}
				} else {
					list.add(new XCoffSymbol(this, name, type, addr, size));
				}

			}
		}
		if (cppfilt != null) {
			cppfilt.dispose();
		}
		if (addr2line != null) {
			addr2line.dispose();
		}
	}

	public Addr2line getAddr2line(boolean autodisposing) {
		if (!autodisposing) {
			XCOFF32Parser parser = (XCOFF32Parser) getBinaryParser();
			return parser.getAddr2line(getPath());
		}
		if (addr2line == null) {
			XCOFF32Parser parser = (XCOFF32Parser) getBinaryParser();
			addr2line = parser.getAddr2line(getPath());
			if (addr2line != null) {
				timestamp = System.currentTimeMillis();
				Runnable worker = new Runnable() {

					public void run() {
						long diff = System.currentTimeMillis() - timestamp;
						while (diff < 10000) {
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
								break;
							}
							diff = System.currentTimeMillis() - timestamp;
						}
						stopAddr2Line();
					}
				};
				new Thread(worker, "Addr2line Reaper").start(); //$NON-NLS-1$
			}
		} else {
			timestamp = System.currentTimeMillis();
		}
		return addr2line;
	}

	synchronized void stopAddr2Line() {
		if (addr2line != null) {
			addr2line.dispose();
		}
		addr2line = null;
	}

	protected CPPFilt getCPPFilt() {
		XCOFF32Parser parser = (XCOFF32Parser) getBinaryParser();
		return parser.getCPPFilt();
	}

	protected Objdump getObjdump() {
		XCOFF32Parser parser = (XCOFF32Parser) getBinaryParser();
		return parser.getObjdump(getPath());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.PlatformObject#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (adapter == Addr2line.class) {
			return getAddr2line(false);
		} else if (adapter == CPPFilt.class) {
			return getCPPFilt();
		}
		return super.getAdapter(adapter);
	}
	
}