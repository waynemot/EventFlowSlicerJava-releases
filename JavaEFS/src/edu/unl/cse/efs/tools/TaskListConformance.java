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
package edu.unl.cse.efs.tools;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.accessibility.AccessibleRole;

import edu.umd.cs.guitar.awb.ActionTypeProvider;
import edu.umd.cs.guitar.awb.JavaActionTypeProvider;
import edu.umd.cs.guitar.graph.converter.EFG2GraphvizEFS;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.*;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.GUIStructureWrapper;
import edu.umd.cs.guitar.model.wrapper.GUITypeWrapper;
import edu.unl.cse.efs.tools.StringTools;
import edu.unl.cse.guitarext.JavaTestInteractions;

/**
 * This class is designed to assist in making TaskList widgets correspond to the contents of EFG files.
 * @author Jonathan Saddler
 *
 */
public class TaskListConformance {


	private static ObjectFactory fact = new ObjectFactory();
	public static String TARGET_PHOLDER_KEYWORD = "Target";

	public static String coreNameOf(String eventId)
	{
		if(eventId == null || eventId.isEmpty())
			return "";
		String subId = eventId;
		if(eventId.contains(GUITARConstants.NAME_VERSION_SEPARATOR))
			subId = subId.substring(eventId.indexOf(GUITARConstants.NAME_VERSION_SEPARATOR)+1);
		int[] seps = StringTools.findNCharactersIn(subId, '_', 2);
		if(seps[1] != -1)
			subId = subId.substring(0, seps[1]);
		return subId;
	}
	/**
	 * Returns true if both eventId1 and eventId2 are both non-null and have matching
	 * core names
	 * @param eventId1
	 * @param eventId2
	 * @return
	 */
	public static boolean matchingNonNullCoreNames(String eventId1, String eventId2)
	{
		if(eventId1 == null || eventId2 == null)
			return false;
		String subId1 = coreNameOf(eventId1);
		String subId2 = coreNameOf(eventId2);
		if(subId1.equals(subId2))
			return true;
		return false;
	}

	/**
	 * Check to see if the attributes of e belong to a widget in the tasklist specified.
	 */
	public static int indexInTasklist(EventType e, TaskList tasklist)
	{
		if(e == null)
			return -1;

		List<Widget> allWidgets = new ArrayList<Widget>(tasklist.getWidget());
		// check to see if the event id of this widget is already supported by the events present in the EFG
		if(e.getEventId() != null && !e.getEventId().isEmpty()) {
			for(int i = 0; i < allWidgets.size(); i++)
				if(matchingNonNullCoreNames(e.getEventId(),allWidgets.get(i).getEventID()))
					return i;
		}
		return -1;
	}

	public static String parseCoreName(String eventId)
	{
		int bookmarkColon = eventId.indexOf(EFG2GraphvizEFS.NAME_VERSION_SEPARATOR);
		String core;
		if(bookmarkColon == -1)
			core = eventId.substring(0, eventId.indexOf(EFG2GraphvizEFS.EVENT_ID_SPLITTER_CHAR));
		else {
			String toConsider = eventId.substring(bookmarkColon+1);
			core = toConsider.substring(0, toConsider.indexOf(EFG2GraphvizEFS.EVENT_ID_SPLITTER_CHAR));
		}
		return core;
	}
	public static List<Widget> normalClickVariantsFromHovers(TaskList tasklist, List<Widget> special)
	{
		List<Widget> clicks = new ArrayList<Widget>();
		for(Widget s : special) {
			Widget testW = s.copyOf(fact);
			String testID = testW.getEventID();
			String coreName = parseCoreName(testID);
			if(coreName.contains(JavaTestInteractions.hoverSurname)) {
				int hIdx = coreName.indexOf(JavaTestInteractions.hoverSurname);
				coreName = coreName.substring(0, hIdx).trim();
				testW.setEventID(coreName);
				int cIdx = indexInTasklist(testW, tasklist);
				clicks.add(tasklist.getWidget().get(cIdx));
			}
		}
		return clicks;
	}

	public static int indexInTasklist(Widget w, TaskList tasklist)
	{
		if(w == null)
			return -1;

		List<Widget> allWidgets = new ArrayList<Widget>(tasklist.getWidget());
		// check to see if the event id of this widget is already supported by the events present in the EFG
		if(w.getEventID() != null && !w.getEventID().isEmpty()) {
			for(int i = 0; i < allWidgets.size(); i++) {
				if(matchingNonNullCoreNames(w.getEventID(),allWidgets.get(i).getEventID())) {
					if(parameterMatch(w, allWidgets.get(i))) {
						return i;
					}
				}
//				boolean matchingNames = matchingNonNullCoreNames(w.getEventID(),allWidgets.get(i).getEventID());
//				boolean matchingParams = (w.getParameter() == null && w.getParameter() == null)
//						|| (w.getParameter() != null && w.getParameter().equals(allWidgets.get(i).getParameter()));
//				if(matchingNames && matchingParams)
//					return i;
			}
		}
		return -1;
	}
	public static boolean parameterMatch(Widget w1, Widget w2)
	{
		if(w1.getParameter() == null || w1.getParameter().isEmpty())
			return w2.getParameter() == null || w2.getParameter().isEmpty();

		else
			return w1.getParameter().equals(w2.getParameter());
	}

	public static List<Widget> orderedWidgets(List<Widget> target, GUIStructure gui, EFG efg, boolean fromRight)
	{
		GUIStructureWrapper gsw = new GUIStructureWrapper(gui);

		List<EventType> allEvents = efg.getEvents().getEvent();
		gsw.parseData();
		LocationComparator xDirectionComparator = new LocationComparator(gsw, target, allEvents);
		ArrayList<Widget> toReturn = new ArrayList<Widget>();
		Collection<LinkedList<Widget>> out = xDirectionComparator.getMappedWidgets(target, fromRight).values();
		for(LinkedList<Widget> o : out)
			for(Widget w : o)
				toReturn.add(w);
		return toReturn;
	}
	/**
	 * What event does this widget's event id point us to
	 * @param w
	 * @return
	 */
	public static int findEvent(Widget w, List<EventType> allEvents)
	{
		for(int i = 0; i < allEvents.size(); i++)
			if(w.getEventID().equals(allEvents.get(i).getEventId())) {
				if(parameterMatch(allEvents.get(i), w))
					return i;
			}
		return -1;
	}
	public static boolean parameterMatch(EventType et, Widget w)
	{
		if(w.getParameter() == null || w.getParameter().isEmpty()) {
			return et.hasAnyParameterLists() == false;
		}
		if(!et.hasAnyParameterLists())
			return false;
		String wParam = w.getParameter();
		String eParam = et.getParameterList().get(0).getParameter().get(0);
		return eParam.equals(wParam);
	}
	/**
	 * Checks to see if w is already supported by the EFG, and if not attempts to ensure that the
	 * w has a matching event in the EFG. <br>If a match is not found, we add a new widget to the tasklist and
	 * return the modified tasklist. Otherwise, we return the tasklist unmodified.
	 */
	public static TaskList checkAndAddByEvent(EventType e, TaskList tasklist, GUIStructureWrapper parsedGSW)
	{
		int wNum = indexInTasklist(e, tasklist);
		if(wNum == -1) // if e is not in tasklist, we will need to add it to the return tasklist.
			tasklist.getWidget().add(createWidgetFromEvent(e, parsedGSW));
		else // if e is in tasklist, simply add this event to the tasklist.
			tasklist.getWidget().get(wNum).setEventID(coreNameOf(e.getEventId()));
		return tasklist;
	}


	public static TaskList coerceEventsAndTasklist(List<EventType> allEvents, TaskList tasklist, GUIStructureWrapper gsw)
	{
		gsw.parseData();
		for(EventType e : allEvents) {
			int wNum = indexInTasklist(e, tasklist);
			if(wNum == -1) // if e is not in tasklist, we will need to add it to the return tasklist.
				tasklist.getWidget().add(createWidgetFromEvent(e, gsw));
			else // if e is in tasklist, simply add this event to the tasklist.
				tasklist.getWidget().get(wNum).setEventID(coreNameOf(e.getEventId()));
		}
		return tasklist;
	}


	public static List<Widget> checkAndSetWidgets(GUIStructure ripperArtifact, EFG workingEventFlow, Widget... widgets)
	{
		return checkAndSetWidgets(Arrays.asList(widgets), ripperArtifact, workingEventFlow);
	}

	/**
	 * *
	 * *
	 * * Carried over from FlowBehind Project
	 * *
	 * *
	 */
	public static List<Widget> checkAndSetWidgets(List<Widget> widgets, GUIStructure ripperArtifact, EFG workingEventFlow)
	{
		GUIStructureWrapper gsw = new GUIStructureWrapper(ripperArtifact);
		gsw.parseData();
		for(int i = 0; i < widgets.size(); i++) {
			Widget w = widgets.get(i);
			widgets.set(i, checkAndSet(w, gsw, workingEventFlow));
		}
		return widgets;
	}


	public static List<EventType> eventsFrom(EFG workingEventFlow, Widget... widgets)
	{
		return eventsFrom(workingEventFlow, Arrays.asList(widgets));
	}
	public static List<EventType> eventsFrom(EFG workingEventFlow, List<Widget> widgets)
	{
		List<EventType> toReturn = new ArrayList<EventType>();
		for(Widget w : widgets) {
			for(EventType et : workingEventFlow.getEvents().getEvent()) {
				if(w.getEventID().equals(et.getEventId())) {
					if(et.hasAnyParameterLists()) {
						String firstParam = et.getParameterList().get(0).getParameter().get(0);
						if(firstParam.equals(w.getParameter())) {
							toReturn.add(et);
							break;
						}
					}
				}
			}
			toReturn.add(fact.createEventType());
		}
		return toReturn;
	}
	public static TaskList checkAndSetConstraintsWidgets(TaskList constraints, GUIStructure ripperArtifact, EFG workingEventFlow)
	{
		GUIStructureWrapper gsw = new GUIStructureWrapper(ripperArtifact);
		gsw.parseData();
		List<Widget> widgetsAtTop = constraints.getWidget();
		if(widgetsAtTop.size() > 0)
			for(int i = 0; i < widgetsAtTop.size(); i++) {
				Widget w = widgetsAtTop.get(i);
				widgetsAtTop.set(i, checkAndSet(w, gsw, workingEventFlow));
			}

		List<Exclusion> exclusions = constraints.getExclusion();
		if(exclusions.size() > 0)
			for(Exclusion exc : exclusions) {
				for(int i = 0; i < exc.getWidget().size(); i++) {
					Widget w = exc.getWidget().get(i);
					exc.getWidget().set(i, checkAndSet(w, gsw, workingEventFlow));
				}
			}

		List<Order> orders = constraints.getOrder();
		if(orders.size() > 0)
			for(Order ord : orders) {
				for(OrderGroup og : ord.getOrderGroup()) {
					for(int i = 0; i < og.getWidget().size(); i++) {
						Widget w = og.getWidget().get(i);
						og.getWidget().set(i, checkAndSet(w, gsw, workingEventFlow));
					}
				}
			}
		List<Atomic> atomics = constraints.getAtomic();
		if(orders.size() > 0)
			for(Atomic atm : atomics) {
				for(AtomicGroup ag : atm.getAtomicGroup()) {
					for(int i = 0; i < ag.getWidget().size(); i++) {
						Widget w = ag.getWidget().get(i);
						ag.getWidget().set(i, checkAndSet(w, gsw,workingEventFlow));
					}
				}
			}
		List<Required> requireds = constraints.getRequired();
		if(requireds.size() > 0)
			for(Required req : requireds) {
				for(int i = 0; i < req.getWidget().size(); i++) {
					Widget w = req.getWidget().get(i);
					req.getWidget().set(i, checkAndSet(w, gsw,workingEventFlow));
				}
			}

		List<Repeat> repeats = constraints.getRepeat();
		if(repeats.size() > 0)
			for(Repeat rep : repeats) {
				for(int i = 0; i < rep.getWidget().size(); i++) {
					Widget w = rep.getWidget().get(i);
					rep.getWidget().set(i, checkAndSet(w, gsw,workingEventFlow));
				}
			}
//		if(repeats != null && repeats.getWidget().size() > 0)
//			for(int i = 0; i < repeats.getWidget().size(); i++) {
//				Widget w = repeats.getWidget().get(i);
//				repeats.getWidget().set(i, checkAndSet(w, gsw,workingEventFlow));
//			}
		return constraints;
	}

	/**
	 * Create a new widget and assign it an event id, type, window, and action according to what we find in the GUI file.
	 */
	public static Widget createWidgetFromEvent(EventType event, GUIStructureWrapper gsw)
	{
		Widget toReturn = fact.createWidget();

		// event id.
		String id = event.getEventId();
		toReturn.setEventID(id);
		PropertyType IDProp = new PropertyType();

		IDProp.setName(GUITARConstants.ID_TAG_NAME);
		IDProp.getValue().add(event.getWidgetId());
		AttributesType idAt = new AttributesType();
		idAt.getProperty().add(IDProp);

		ComponentTypeWrapper wXML = gsw.getComponentBySignaturePreserveTree(idAt);

		// type
		if(wXML != null) {
			PropertyType type = wXML.getFirstPropertyByName(GUITARConstants.CLASS_TAG_NAME);
			if(type != null && !type.getValue().isEmpty())
				toReturn.setType(type.getValue().get(0));
		}
		else if(event.getOptional() != null) { // backup, use the optional field.
			AttributesTypeWrapper atw = new AttributesTypeWrapper(event.getOptional());
			String type = atw.getFirstValByName(GUITARConstants.CLASS_TAG_NAME);
			if(type != null && !type.isEmpty())
				toReturn.setType(type);
		}

		// name
		if(wXML != null) {
			PropertyType type = wXML.getFirstPropertyByName(GUITARConstants.TITLE_TAG_NAME);
			if(type != null && !type.getValue().isEmpty())
				toReturn.setName(type.getValue().get(0));
		}
		else if(event.getOptional() != null) {
			AttributesTypeWrapper atw = new AttributesTypeWrapper(event.getOptional());
			String type = atw.getFirstValByName(GUITARConstants.TITLE_TAG_NAME);
			if(type != null && !type.isEmpty())
				toReturn.setName(type);
		}

		// window
		if(wXML != null) {
			GUITypeWrapper wWindow = wXML.getWindow();
			if(wWindow != null)
				toReturn.setWindow(wXML.getWindow().getTitle());
		}


		// action.
		String action = JavaActionTypeProvider.getTypeFromActionHandler(event.getAction());
		toReturn.setAction(action);

		return toReturn;
	}

	public static AttributesType getGUITARIDSignature(EventType e)
	{
		PropertyType IDProp = new PropertyType();
		IDProp.setName(GUITARConstants.ID_TAG_NAME);
		IDProp.getValue().add(e.getWidgetId());
		AttributesType idAt = new AttributesType();
		idAt.getProperty().add(IDProp);
		return idAt;
	}
	public static AttributesType getGUITARCTHIDSignature(Widget w)
	{
		PropertyType IDProp = new PropertyType();

		IDProp.setName(GUITARConstants.CTH_EVENT_ID_NAME);

		int colIdx = w.getEventID().indexOf(':');
		if(colIdx != -1)
			IDProp.getValue().add(w.getEventID().substring(colIdx+1));

		else
			IDProp.getValue().add(w.getEventID());
		AttributesType idAt = new AttributesType();
		idAt.getProperty().add(IDProp);
		return idAt;
	}
	/**
	 * Based on the signature of the widget specified (the attributes defined in the xml object),
	 * retrieve an event id corresponding to an event in the events list that matches the widget's signature,
	 * given certain criteria about the widget found in the TaskList XML file.<br>
	 * If no event was found that could match w, return -1. <br>Otherwise
	 * returns the index in the event list we wish to assign to w, and modifies the input parameter to contain the
	 * event id of the event at the index returned.
	 */
	public static int assignEventIdBySignature(Widget w, List<EventType> allEvents, GUIStructureWrapper guiStructureAdapter)
	{
		for(int i = 0; i < allEvents.size(); i++) {
			EventType e = allEvents.get(i);
			PropertyType IDProp = new PropertyType();
			IDProp.setName(GUITARConstants.ID_TAG_NAME);
			IDProp.getValue().add(e.getWidgetId());
			AttributesType idAt = new AttributesType();
			idAt.getProperty().add(IDProp);
			ComponentTypeWrapper wXML = guiStructureAdapter.getComponentBySignaturePreserveTree(idAt);
			// action handler.
			String actionHandler = "";
			String[] tokens = e.getEventId().split(EFG2GraphvizEFS.NAME_VERSION_SEPARATOR);
			tokens = tokens[0].split(GUITARConstants.NAME_SEPARATOR);
			int eventInd = -1;
			if(tokens.length > 1)
				eventInd = Integer.parseInt(tokens[1]);
			if(eventInd>=0){
				PropertyType prop = wXML.getFirstPropertyByName(GUITARConstants.EVENT_TAG_NAME);
				if(prop!=null) {
					List<String> values = prop.getValue();
					if(eventInd<values.size())
						actionHandler = values.get(eventInd);
				}
			}
			// parent
			String parentName = "";
			ComponentTypeWrapper parent = wXML.getParent();
			if(parent!=null)
				parentName = parent.getFirstValueByName(GUITARConstants.TITLE_TAG_NAME);


			// detect whether this event matches this widget.
			String title = wXML.getFirstValueByName(GUITARConstants.TITLE_TAG_NAME);
			String type = wXML.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);
			if(type.equals(AccessibleRole.DIALOG.toDisplayString())
			|| type.equals(AccessibleRole.FRAME.toDisplayString()))
				type = AccessibleRole.WINDOW.toDisplayString();

			// find GUI parent parameter.
			GUITypeWrapper window = wXML.getWindow();
			String windowTitle = window.getTitle();

			boolean specialActionPresent = actionHandler != null && !actionHandler.isEmpty();
			boolean parentPresentWidget = w.getParent() != null && !w.getParent().isEmpty();
			boolean titleEqual = title != null && title.equals(w.getName());
			boolean typeEqual = type != null && type.equals(w.getType());
//			boolean windowEqual = windowTitle != null && windowTitle.equals(w.getWindow());
			boolean windowEqual = JavaTestInteractions.windowTitlesAreSame(windowTitle, w.getWindow());
			if(titleEqual && typeEqual && windowEqual) {
				if(specialActionPresent && parentPresentWidget) {
					String jDerivedHandler = JavaActionTypeProvider.getActionHandler(w.getAction());
					String oDerivedHandler = ActionTypeProvider.getActionHandler(w.getAction());
					if(parentName.equals(w.getParent()) && (actionHandler.equals(jDerivedHandler) || actionHandler.equals(oDerivedHandler)))
						return i;
					// checked whether title, type, window, parent, and actionHandler match.
				}
				else if(parentPresentWidget){ // parent present
					if(parentName.equals(w.getParent()))
						return i;
					// checked whether title, type, window, and parent match.
				}
				else if(specialActionPresent) {
					String jDerivedHandler = JavaActionTypeProvider.getActionHandler(w.getAction());
					String oDerivedHandler = ActionTypeProvider.getActionHandler(w.getAction());
					if(actionHandler.equals(jDerivedHandler) || actionHandler.equals(oDerivedHandler))
						return i;
					// checked whether title, type, window, and actionHandler match
				}
				else
					return i;
					// checked whether only title, type, and window match.
			}
		} // end for.
		return -1; // no event id was assigned to this widget.
	}

	public static ComponentTypeWrapper getComponentByEvent(EventType e, GUIStructureWrapper guiStructureAdapter)
	{
		PropertyType IDProp = new PropertyType();
		IDProp.setName(GUITARConstants.ID_TAG_NAME);
		IDProp.getValue().add(e.getWidgetId());
		AttributesType idAt = new AttributesType();
		idAt.getProperty().add(IDProp);
		ComponentTypeWrapper wXML = guiStructureAdapter.getComponentBySignaturePreserveTree(idAt);
		return wXML;
	}

	/**
	 * Checks to see if w is already supported by the EFG, and if not attempts to ensure that the
	 * w has a matching event in the EFG. <br>If a match is found, w's eventID is set to match the
	 * corresponding eventId in the EFG. If a match is not found, w's eventID is set to the string &quot;(none)&quot;.
	 */
	public static Widget checkAndSet(Widget w, GUIStructureWrapper gsw, EFG workingEventFlow)
	{
		if(w == null) throw new IllegalArgumentException("Error: Empty Widget element found in TaskList");
		List<EventType> allEvents = workingEventFlow.getEvents().getEvent();
		// check to see if the event id of this widget is already supported by the events present in the EFG
		if(w.getEventID() != null && !w.getEventID().isEmpty()) {
			for(int i = 0; i < allEvents.size(); i++)
				if(w.getEventID().equals(allEvents.get(i).getEventId()))
					return w;
					// if it is, we have returned the widget without modifying it.
		}
		// if it's not, we will need to assign it an event id, or overwrite with the special name (none).
		int eventNum = assignEventIdBySignature(w, allEvents, gsw);
		if(eventNum == -1) {
			w.setEventID("(none)");
		}
		else {
			w.setEventID(allEvents.get(eventNum).getEventId());
		}
		return w;
	}
}
