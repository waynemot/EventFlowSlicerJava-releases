/*******************************************************************************
 *    Copyright (c) 2018 Jonathan A. Saddler
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *    
 *    Contributors:
 *     Jonathan A. Saddler - initial API and implementation
 *******************************************************************************/
package edu.unl.cse.guitarext;

/**
 * Source for the HeadTable class. The head table is an object that stores a JavaTestInteractions instance
 * that is always available from any class within the project. 
 * @author jsaddle
 *
 */
public class HeadTable {
	public static java.util.List<JavaTestInteractions> allInteractions;
	
	public static JavaTestInteractions getInteractionsForWindowName(String name) 
	{
		for(JavaTestInteractions jti : allInteractions) 
			for(java.awt.Window w : jti.getWindowsScanned()) {
				String windowName = w.getAccessibleContext().getAccessibleName();
//				if(windowName != null && windowName.equals(name)) 
				if(windowName != null && JavaTestInteractions.windowTitlesAreSame(windowName, name))
					return jti;
			}
		return new JavaTestInteractions();
	}
}
