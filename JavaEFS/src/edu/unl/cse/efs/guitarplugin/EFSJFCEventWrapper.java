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

package edu.unl.cse.efs.guitarplugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.accessibility.AccessibleRole;

import edu.umd.cs.guitar.event.ActionClass;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.EventWrapper;
import edu.umd.cs.guitar.model.wrapper.GUITypeWrapper;

import static edu.umd.cs.guitar.model.GUITARConstants.FOLLOW_EDGE;
import static edu.umd.cs.guitar.model.GUITARConstants.NO_EDGE;
import static edu.umd.cs.guitar.model.GUITARConstants.REACHING_EDGE;
import static javax.accessibility.AccessibleRole.*;

/**
 * This class is used to override certain methods in order to support the event flow graph structure supported
 * by Cogtool-helper's replay functionality, for EventWrappers created from GUIStructures ripped from JFC AWT Swing graphical
 * user interfaces.
 * @author Jonathan Saddler
 */
public class EFSJFCEventWrapper extends EventWrapper
{
	/**
	 * An enumeration that comprises a decision of how edges leaving a component should
	 * enter another node, depending on whether the second node is nested underneath a tab
	 * or not.
	 */
	private static enum TabSafety {TABLESS, TAB_SAFE, NON_TAB_SAFE};

	@Override
	public int isFollowedBy(EventWrapper other)
	{
		// Component associated with the first event
		ComponentTypeWrapper firstComponent = this.component;
		GUITypeWrapper firstWindow = firstComponent.getWindow();

		// Component associated with the second event
		ComponentTypeWrapper secondComponent = other.getComponent();
		GUITypeWrapper secondWindow = secondComponent.getWindow();

		// e2 is a keyboard event
		String e2Action = other.getAction();
		boolean keyb = false;
		if(e2Action.contains("Keyboard")){
			keyb = true;
			String type = this.getType();
			if(firstWindow.equals(secondWindow)){
				if(type.equals(GUITARConstants.EXPAND)&&!this.getAction().contains("Text")){
					return NO_EDGE;
				}
				else if(type.equals(GUITARConstants.RESTRICED_FOCUS)){
					return NO_EDGE;
				}else if(type.equals(GUITARConstants.TERMINAL)){
					return NO_EDGE;
				}else{
					return FOLLOW_EDGE;
				}
			}
		}

		if(!keyb){
			String thisRole = firstComponent.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);
			String secondRole = secondComponent.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);

			// Case 1: first event is a combo box action, or second event is a combo box select.

			if(thisRole != null && thisRole.equals(AccessibleRole.COMBO_BOX.toDisplayString())) {
				String secondID = secondComponent.getFirstValueByName(GUITARConstants.ID_TAG_NAME);
				if(ID.equals(secondID)) {
					if(action.equals(ActionClass.ACTION.actionName) && other.getAction().equals(ActionClass.PARSELECT.actionName))
						return REACHING_EDGE; // second is a combo child of the first, and is yet hidden.
					else if(action.equals(ActionClass.PARSELECT.actionName) && other.getAction().equals(ActionClass.PARSELECT.actionName))
						return NO_EDGE; // can't click on selection twice.
				}
				else if(action.equals(ActionClass.ACTION.actionName)) {
					return NO_EDGE; // follow edges from combo boxes have to lead out to combo box select events only.
				}
			}
			else if(secondRole != null)
				if(secondRole.equals(AccessibleRole.COMBO_BOX.toDisplayString())
				&& other.getAction().equals(ActionClass.PARSELECT.actionName))
					return NO_EDGE; // can't reach a combo select other than from the combo box click.

			// -----------------------------------
			// Case 2: e2 is a hidden event and e2 is a direct child of e1

			// Get direct parent of event 2

			// if e2 has a parent: it is an event wrapped in its parent's component
			ComponentTypeWrapper directParent = directParentOf(other);

			if (directParent != null) {
				String parentID = directParent.getFirstValueByName(GUITARConstants.ID_TAG_NAME);
				if (ID.equals(parentID))
					return REACHING_EDGE; // self edge

//				String parentID = directParent.getFirstValueByName(GUITARConstants.ID_TAG_NAME);
//				String thisWID = component.getFirstValueByName(GUITARConstants.ID_TAG_NAME);
//
//				if(parentID.equals(thisWID)) {
//					// self edge
//					String action = getAction();
//					if(action != null && action.equals(ActionClass.WINDOW.actionName)) // can't close yourself twice
//						return NO_EDGE;
//				}

			}



			// -----------------------------------------------
			// Case 2: e2 is not a hidden event and in the windows available
			// after e1 is performed


			switch(tabSafetyOfTransition(this, other)) {
			case TAB_SAFE		:	return FOLLOW_EDGE;
			case NON_TAB_SAFE 	:	return NO_EDGE;
			default				:	// continue the method.
			}


			// first event type
			String firstEventType = firstComponent.getFirstValueByName(GUITARConstants.TYPE_TAG_NAME);

			// -------------------------------------
			// Get the top window list available after the first event (note that
			// it is a list since one event may open several window at the same time

			Set<GUITypeWrapper> windowsTopAfterFirstEvent = new HashSet<GUITypeWrapper>();

			// ------------------------
			// Check invoked windows
			List<GUITypeWrapper> windowsInvoked = firstComponent.getInvokedWindows();

			// Terminal event, current window is closed
			if (GUITARConstants.TERMINAL.equals(firstEventType)) {
				ComponentTypeWrapper invoker = firstWindow.getInvoker();
				if (invoker != null)
					windowsTopAfterFirstEvent.add(invoker.getWindow());
			}
			// Non terminal
			else {
				// New window opened
				if (windowsInvoked.size() > 0)
					windowsTopAfterFirstEvent.addAll(windowsInvoked);
				// No window opened/closed
				else
					windowsTopAfterFirstEvent.add(firstWindow);
			}

			// windows have been collected.

			// -----------------------------------------
			// Check if the 2nd event is in the opened windows
			if (windowsInvoked.size() > 0)
				for (GUITypeWrapper window : windowsInvoked) {
					if (window.equals(secondWindow)) {
						return GUITARConstants.REACHING_EDGE;
					}
				}

			// -----------------------------------------
			// Check if the 2nd event is reachable after the 1st one

			Set<GUITypeWrapper> windowsAvailableAfterFirstEvent = new HashSet<GUITypeWrapper>();
			// get all windows available after event 1
			for (GUITypeWrapper window : windowsTopAfterFirstEvent) {
				Set<GUITypeWrapper> avaiableWindows = window.getAvailableWindowList();
				if (avaiableWindows != null)
					windowsAvailableAfterFirstEvent.addAll(avaiableWindows);
			}

			// Check if the 2nd event is in the list of available window after the
			// 1st event

//			for (GUITypeWrapper window : windowsAvailableAfterFirstEvent)
//				if (window.equals(secondWindow)) {
//					return FOLLOW_EDGE;
//				}
			for (GUITypeWrapper window : windowsAvailableAfterFirstEvent)
				if (window.equals(secondWindow)) {
					return FOLLOW_EDGE;
				}
				else if(window.isModal())
					return NO_EDGE;


//			return NO_EDGE;
			return FOLLOW_EDGE;
		}
		return NO_EDGE;
	}


	public boolean isHidden()
	{
		if(this.action.contains("Keyboard")){
			return false;
		}

		ComponentTypeWrapper parent = this.component.getParent();
		while (parent != null) {
			String action = parent
					.getFirstValueByName(GUITARConstants.EVENT_TAG_NAME);
			if (action != null && !action.equals(ActionClass.WINDOW.actionName)) {
				return true;
			}
			parent = parent.getParent();
		}

		String compClass = this.component.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);
		if(compClass != null && compClass.equals(AccessibleRole.COMBO_BOX.toDisplayString()))
			if(action != null && action.equals(ActionClass.PARSELECT.actionName))
				return true;
		return false;

	}

	/**
	 * Is the action to transition from first component to second component tab safe?
	 * If firstComponent and secondComponent are both components that reside in tabs, can a transition be made
	 * from firstComponent to second component?
	 * @return
	 */
	private static TabSafety tabSafetyOfTransition(EventWrapper first, EventWrapper other)
	{
		ComponentTypeWrapper firstComponent = first.getComponent();
		ComponentTypeWrapper secondComponent = other.getComponent();
		String firstRole = firstComponent.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);
		GUITypeWrapper firstWindow = firstComponent.getWindow();
		GUITypeWrapper secondWindow = secondComponent.getWindow();

		ComponentTypeWrapper firstSubTabComp = nearestBindingChild(firstComponent);
		String firstParentRole = parentRoleOf(firstSubTabComp);
		ComponentTypeWrapper secondSubTabComp = nearestBindingChild(secondComponent);
		String secondParentRole = parentRoleOf(secondSubTabComp);
		boolean firstHasTab = firstParentRole.equals(PAGE_TAB_LIST.toDisplayString());
		boolean secondHasTab = secondParentRole.equals(PAGE_TAB_LIST.toDisplayString());
		boolean firstIsTab = firstRole.equals(PAGE_TAB_LIST.toDisplayString());
		String firstId = firstComponent.getFirstValueByName(GUITARConstants.ID_TAG_NAME);


		if(!firstWindow.equals(secondWindow)) {
			if(secondHasTab)
				return TabSafety.NON_TAB_SAFE; // second element sits under another window's page tab list.
			else
				return TabSafety.TABLESS; // we're not entering a new tab list, so the event is tab-independent.
		}
		if(firstHasTab && secondHasTab) {
			String firstSubId = firstSubTabComp.getFirstValueByName(GUITARConstants.ID_TAG_NAME);
			String secondSubId = secondSubTabComp.getFirstValueByName(GUITARConstants.ID_TAG_NAME);
			if(firstIsTab) {
				String tabListId = secondSubTabComp.getParent().getFirstValueByName(GUITARConstants.ID_TAG_NAME);
				if(tabListId.equals(firstId))
					return TabSafety.TAB_SAFE; // second belongs to a tab in first's page tab list.
			}
			if(firstSubId.equals(secondSubId))
				return TabSafety.TAB_SAFE; // the two elements are in the same tab.
			else
				return TabSafety.NON_TAB_SAFE; // the two elements aren't in the same tab.
		}
		if(firstHasTab && !secondHasTab) {
			if(!other.isHidden())
				return TabSafety.TAB_SAFE; // exiting a tab by clicking some other widget outside the current page tab.
			else
				return TabSafety.NON_TAB_SAFE; // cannot access a hidden element from a page tab.
		}
		else if(!firstHasTab && secondHasTab) {
//			return follow_edge if the first is a page tab, and that the second has a page tab child matching one of the first’s children.
			if(!firstIsTab)
				return TabSafety.NON_TAB_SAFE; // can't reach an element in a page tab list, unless we click the page tab first.
			String tabListId = secondSubTabComp.getParent().getFirstValueByName(GUITARConstants.ID_TAG_NAME);
			if(tabListId.equals(firstId))
				return TabSafety.TAB_SAFE; // second belongs to a tab in first's page tab list.
			else
				return TabSafety.NON_TAB_SAFE; // second does not belong to a tob in first's page tab listString targetId = firstSubTabComp.getFirstValueByName(GUITARConstants.ID_TAG_NAME);
		}
		else
			return TabSafety.TABLESS; // else both events are tabless, don't check anything in this phase.
	}

	public ComponentTypeWrapper directParentOf(EventWrapper event)
	{

		// find direct parent.
		ComponentTypeWrapper directParent = event.getComponent().getParent();
		while (directParent != null) {
			String action = directParent.getFirstValueByName(GUITARConstants.EVENT_TAG_NAME);
			String role = directParent.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);
//			if (action != null && !role.equals(AccessibleRole.PAGE_TAB_LIST.toDisplayString()))
			if (action != null
			&& !role.equals(AccessibleRole.PAGE_TAB_LIST.toDisplayString())
			&& !role.equals(AccessibleRole.PANEL.toDisplayString()))
				break; // if we're under a page tab or panel then we're really not "hidden", but there are constraints we heed to.
			directParent = directParent.getParent();
		}
		return directParent;
	}

	/**
	 * Return the container that sits just beneath the smallest actionable container, be it a page tab or a window,
	 * that contains component.
	 */
	private static ComponentTypeWrapper nearestBindingChild(ComponentTypeWrapper component)
	{
		if(component == null)
			return null; // we have chosen explicitly to avoid throwing exceptions from this method.
		// find direct parent.
		String thisRole = component.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);

		ComponentTypeWrapper directParent = component.getParent();
		ComponentTypeWrapper toReturn = component;
		if(
			thisRole.equals(FRAME.toDisplayString()) ||
			thisRole.equals(DIALOG.toDisplayString()) ||
			thisRole.equals(WINDOW.toDisplayString())) {
				return toReturn;
		}
		while (directParent != null) {
//			String action = directParent.getFirstValueByName(GUITARConstants.EVENT_TAG_NAME);
			String role = directParent.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);
			if (role.equals(PAGE_TAB_LIST.toDisplayString())
				|| role.equals(FRAME.toDisplayString())
				|| role.equals(DIALOG.toDisplayString())
				|| role.equals(WINDOW.toDisplayString())
			)
				break; // if we're under a page tab then we're really not "hidden", but there are constraints we heed to.
			toReturn = directParent;
			directParent = directParent.getParent();
		}
//		if(toReturn != null && toReturn.getDComponentType() instanceof ContainerType) {
//			toReturn = ((ContainerType)toReturn.getDComponentType()).getContents().get
//		}
		return toReturn;
	}
	/**
	 * Return the role of component's parent.
	 */
	private static String parentRoleOf(ComponentTypeWrapper component)
	{
		String toReturn = "";
		if(component != null) {
			ComponentTypeWrapper parent = component.getParent();
			if(parent == null)
				toReturn = "";
			else {
				toReturn = parent.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);
				if(toReturn == null)
					toReturn = "";
			}
		}
		return toReturn;
	}
	public String toString()
	{
		if(component == null)
			return "";
		return component.toString();
	}
}
