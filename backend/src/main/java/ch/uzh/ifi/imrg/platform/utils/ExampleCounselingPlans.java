package ch.uzh.ifi.imrg.platform.utils;

public class ExampleCounselingPlans {

  public static String getCounselingPlanSystemPrompt() {
    StringBuilder sb = new StringBuilder();

    sb.append(
        "You are an expert AI assistant specializing in Cognitive Behavioral Therapy (CBT).\n\n");

    sb.append("--- REFERENCE EXAMPLES ---\n");
    sb.append(
        "The following are high-quality example counseling plans. Use them as a reference for tone, structure, and content.\n\n");
    sb.append(ExampleCounselingPlans.getExampleCounselingPlans());
    return sb.toString();
  }

  public static String getExampleCounselingPlans() {
    StringBuilder sb = new StringBuilder();

    // Plan 1: Generalized Anxiety Disorder (GAD)
    sb.append("--- Example Counseling Plan 1 (Generalized Anxiety Disorder) ---\n");
    sb.append("Counseling start: 2025-08-01T09:00:00Z\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Psychoeducation and Assessment\n");
    sb.append("    Counseling Plan phase duration in weeks: 2\n");
    sb.append("    Counseling Plan phase number: 1\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Understand the CBT Model of GAD\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will be able to explain the relationship between their thoughts (worries), feelings (anxiety), physical sensations (restlessness), and behaviors (avoidance, seeking reassurance).\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Identify and Monitor Worries\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will accurately track their worry episodes, including triggers, content, and intensity, using a daily log.\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Worry Diary\n");
    sb.append(
        "        Exercise Description: For the next two weeks, whenever you notice yourself worrying, take a moment to write down what you are worried about, what triggered the worry, and how anxious it makes you feel on a scale of 0-100.\n");
    sb.append(
        "        Exercise Explanation: This exercise helps us gather data to understand the patterns of your worry. It's the first step in learning to manage it.\n");
    sb.append("        Exercise Start: 2025-08-01T10:00:00Z\n");
    sb.append("        Exercise End: 2025-08-15T10:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 1\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Cognitive and Behavioral Interventions\n");
    sb.append("    Counseling Plan phase duration in weeks: 8\n");
    sb.append("    Counseling Plan phase number: 2\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Challenge Maladaptive Worry Beliefs\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will learn to identify cognitive distortions related to their worries (e.g., catastrophizing, probability overestimation) and develop more balanced alternative thoughts.\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Implement Behavioral Strategies\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will practice techniques like 'Scheduled Worry Time' and problem-solving to reduce the overall time spent worrying and increase their sense of control.\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Cognitive Restructuring for Worry\n");
    sb.append(
        "        Exercise Description: Use the provided worksheet to challenge a key worry each day. Identify the automatic thought, list the evidence for and against it, identify any cognitive distortions, and formulate a more balanced, realistic thought.\n");
    sb.append(
        "        Exercise Explanation: This technique directly targets the 'cognitive' part of CBT, helping you to question the validity of your worries and reduce their power.\n");
    sb.append("        Exercise Start: 2025-08-16T10:00:00Z\n");
    sb.append("        Exercise End: 2025-10-10T10:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 1\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Scheduled Worry Time\n");
    sb.append(
        "        Exercise Description: Designate a specific 15-minute period each day (e.g., 5:00 PM - 5:15 PM) as your 'Worry Time'. When worries pop up outside this time, jot them down and postpone engaging with them until your scheduled time.\n");
    sb.append(
        "        Exercise Explanation: This helps contain worry to a specific period, preventing it from dominating your entire day. It teaches you that you have control over when you engage with worry.\n");
    sb.append("        Exercise Start: 2025-08-23T10:00:00Z\n");
    sb.append("        Exercise End: 2025-10-10T10:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 1\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Relapse Prevention\n");
    sb.append("    Counseling Plan phase duration in weeks: 2\n");
    sb.append("    Counseling Plan phase number: 3\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append(
        "        Counseling Plan Phase Goal name: Consolidate Skills and Create a Wellness Plan\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will summarize their key learnings and create a personal 'Wellness Plan' outlining how they will continue to use CBT skills to manage worry and stress in the future.\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Creating a Personal Wellness Plan\n");
    sb.append(
        "        Exercise Description: Based on what worked best for you in therapy, create a document outlining your strategies for managing future stress and worries. Include your personal warning signs and a step-by-step plan of action.\n");
    sb.append(
        "        Exercise Explanation: This blueprint will be your guide to maintaining your progress after therapy ends, empowering you to be your own therapist.\n");
    sb.append("        Exercise Start: 2025-10-11T10:00:00Z\n");
    sb.append("        Exercise End: 2025-10-24T10:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 7\n");
    sb.append("\n\n"); // Separator

    // Plan 2: Panic Disorder with Agoraphobia
    sb.append("--- Example Counseling Plan 2 (Panic Disorder with Agoraphobia) ---\n");
    sb.append("Counseling start: 2025-09-05T14:00:00Z\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Psychoeducation and The Vicious Cycle of Panic\n");
    sb.append("    Counseling Plan phase duration in weeks: 3\n");
    sb.append("    Counseling Plan phase number: 1\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Understand the Panic Cycle\n");
    sb.append(
        "        Counseling Plan Phase Goal description: Patient will be able to map their own panic attacks onto the 'Vicious Cycle' model: Trigger -> Perceived Threat -> Bodily Sensations -> Catastrophic Misinterpretation -> Increased Anxiety -> More Sensations.\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Identify Safety Behaviors and Avoidance\n");
    sb.append(
        "        Counseling Plan Phase Goal description: Patient will create a comprehensive list of situations they avoid and the 'safety behaviors' they use (e.g., carrying water, sitting near exits, checking pulse).\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Panic Attack Record\n");
    sb.append(
        "        Exercise Description: After any panic attack or high-anxiety episode, fill out the Panic Attack Record. Detail the situation, physical sensations, thoughts ('I'm having a heart attack'), and behaviors (what you did to cope).\n");
    sb.append(
        "        Exercise Explanation: This helps us understand the specific thoughts that fuel your panic, which is crucial for learning how to challenge them.\n");
    sb.append("        Exercise Start: 2025-09-05T15:00:00Z\n");
    sb.append("        Exercise End: 2025-09-26T15:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 1\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Exposure and Cognitive Restructuring\n");
    sb.append("    Counseling Plan phase duration in weeks: 9\n");
    sb.append("    Counseling Plan phase number: 2\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Reduce Fear of Physical Sensations\n");
    sb.append(
        "        Counseling Plan Phase Goal description: Patient will systematically induce feared physical sensations (e.g., dizziness, rapid heartbeat) in a controlled way through interoceptive exposure, learning that they are not dangerous.\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Confront Feared Situations\n");
    sb.append(
        "        Counseling Plan Phase Goal description: Patient will gradually face situations on their avoidance hierarchy (in-vivo exposure) without using safety behaviors, leading to habituation of the fear response.\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Interoceptive Exposure\n");
    sb.append(
        "        Exercise Description: Three times a week, perform one exercise from your interoceptive hierarchy. For example: hyperventilate for 60 seconds to induce lightheadedness. Rate your anxiety before, during, and after, and refrain from any safety behaviors.\n");
    sb.append(
        "        Exercise Explanation: This breaks the link between physical sensations and catastrophic thoughts. You learn through direct experience that these feelings, while uncomfortable, are harmless.\n");
    sb.append("        Exercise Start: 2025-09-27T15:00:00Z\n");
    sb.append("        Exercise End: 2025-11-28T15:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 2\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: In-Vivo Exposure Hierarchy\n");
    sb.append(
        "        Exercise Description: Once a week, complete one step from your agoraphobia hierarchy. Start with something low-anxiety (e.g., standing outside the supermarket for 5 mins) and work your way up to higher-anxiety tasks (e.g., taking the bus for 3 stops).\n");
    sb.append(
        "        Exercise Explanation: This gradually retrains your brain to learn that the situations you fear are actually safe, reducing both panic and avoidance.\n");
    sb.append("        Exercise Start: 2025-10-04T15:00:00Z\n");
    sb.append("        Exercise End: 2025-11-28T15:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 7\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Relapse Prevention and Independence\n");
    sb.append("    Counseling Plan phase duration in weeks: 2\n");
    sb.append("    Counseling Plan phase number: 3\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Maintain Gains and Plan for Setbacks\n");
    sb.append(
        "        Counseling Plan Phase Goal description: Patient will create a personal Panic Management Plan, identifying high-risk situations for the future and outlining specific strategies to cope with potential setbacks without reverting to avoidance.\n");
    sb.append("\n\n"); // Separator

    // Plan 3: Major Depressive Disorder (MDD)
    sb.append("--- Example Counseling Plan 3 (Major Depressive Disorder) ---\n");
    sb.append("Counseling start: 2025-07-30T11:00:00Z\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append(
        "    Counseling Plan phase name: Assessment and Behavioral Activation Introduction\n");
    sb.append("    Counseling Plan phase duration in weeks: 2\n");
    sb.append("    Counseling Plan phase number: 1\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append(
        "        Counseling Plan Phase Goal name: Understand the Depression-Inactivity Cycle\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will understand how low mood leads to reduced activity, which in turn maintains or worsens the low mood.\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Establish a Baseline\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will monitor their daily activities and mood ratings to establish a baseline and identify any existing links between specific activities and changes in mood.\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Activity and Mood Monitoring\n");
    sb.append(
        "        Exercise Description: For the next week, use an hourly log to track your activities. For each activity, rate your mood (0-10) and sense of pleasure (P) and accomplishment (A) from 0-10.\n");
    sb.append(
        "        Exercise Explanation: This helps us see the direct impact of what you do on how you feel. It's the foundation for actively scheduling mood-boosting activities.\n");
    sb.append("        Exercise Start: 2025-07-30T12:00:00Z\n");
    sb.append("        Exercise End: 2025-08-13T12:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 1\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Behavioral Activation and Cognitive Work\n");
    sb.append("    Counseling Plan phase duration in weeks: 10\n");
    sb.append("    Counseling Plan phase number: 2\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Increase Rewarding Activities\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will systematically schedule and engage in activities that provide a sense of pleasure (enjoyment) and mastery (accomplishment), based on their values.\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append(
        "        Counseling Plan Phase Goal name: Identify and Challenge Negative Automatic Thoughts (NATs)\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will learn to catch, check, and change depressogenic thoughts (e.g., 'I am a failure,' 'Nothing will ever get better') using a thought record.\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Weekly Activity Scheduling\n");
    sb.append(
        "        Exercise Description: At the start of each week, plan and schedule specific activities from your 'Pleasure' and 'Mastery' lists into your diary. Treat these as important appointments. Carry them out regardless of your mood.\n");
    sb.append(
        "        Exercise Explanation: This is Behavioral Activation. It works on the principle 'action before motivation'. By engaging in rewarding behaviors, you directly combat withdrawal and anhedonia, leading to improved mood.\n");
    sb.append("        Exercise Start: 2025-08-14T12:00:00Z\n");
    sb.append("        Exercise End: 2025-10-22T12:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 7\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Dysfunctional Thought Record\n");
    sb.append(
        "        Exercise Description: When you notice a significant dip in your mood, use the 7-column thought record to dissect the experience. Note the situation, mood, automatic thought(s), evidence for/against the thought, and a balanced alternative thought.\n");
    sb.append(
        "        Exercise Explanation: This core CBT technique helps you step back from your negative thoughts and evaluate them objectively, reducing their believability and emotional impact.\n");
    sb.append("        Exercise Start: 2025-08-28T12:00:00Z\n");
    sb.append("        Exercise End: 2025-10-22T12:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 2\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Consolidation and Relapse Prevention\n");
    sb.append("    Counseling Plan phase duration in weeks: 2\n");
    sb.append("    Counseling Plan phase number: 3\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append(
        "        Counseling Plan Phase Goal name: Develop a Depression Relapse Prevention Plan\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will create a comprehensive plan that includes recognizing personal warning signs of a relapse and a menu of behavioral and cognitive strategies to deploy when these signs are noticed.\n");
    sb.append("\n\n"); // Separator

    // Plan 4: Social Anxiety Disorder (SAD)
    sb.append("--- Example Counseling Plan 4 (Social Anxiety Disorder) ---\n");
    sb.append("Counseling start: 2025-08-18T16:00:00Z\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Psychoeducation and Goal Setting\n");
    sb.append("    Counseling Plan phase duration in weeks: 3\n");
    sb.append("    Counseling Plan phase number: 1\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Understand the Cognitive Model of SAD\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will be able to describe how their pre- and post-event processing, self-focused attention, and distorted predictions maintain their social anxiety.\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Develop a Social Situation Hierarchy\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will create a ranked list of feared social situations, from least to most anxiety-provoking, which will serve as a roadmap for behavioral experiments.\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Social Anxiety Thought Record\n");
    sb.append(
        "        Exercise Description: Before and after a social situation, record your predictions about what will happen (e.g., 'I'll say something stupid and everyone will laugh') and then record what actually happened. Compare the prediction with the outcome.\n");
    sb.append(
        "        Exercise Explanation: This exercise highlights the discrepancy between your anxious predictions and reality, weakening the beliefs that fuel your social fear.\n");
    sb.append("        Exercise Start: 2025-08-18T17:00:00Z\n");
    sb.append("        Exercise End: 2025-09-05T17:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 3\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append(
        "    Counseling Plan phase name: Cognitive Restructuring and Behavioral Experiments\n");
    sb.append("    Counseling Plan phase duration in weeks: 8\n");
    sb.append("    Counseling Plan phase number: 2\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Challenge Negative Social Beliefs\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will actively challenge their core beliefs about social performance (e.g., 'I must always be witty and interesting') and the perceived consequences of social blunders.\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Reduce Safety Behaviors and Avoidance\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will conduct a series of behavioral experiments from their hierarchy, intentionally dropping safety behaviors (e.g., rehearsing sentences, avoiding eye contact) to test their negative predictions.\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Behavioral Experiments\n");
    sb.append(
        "        Exercise Description: Each week, select an item from your social hierarchy. Define a specific, testable prediction (e.g., 'If I ask a question in a meeting, people will think I'm incompetent'). Run the experiment (ask the question). Observe and record the actual outcome.\n");
    sb.append(
        "        Exercise Explanation: Unlike simple exposure, behavioral experiments are designed to test specific beliefs. You act like a scientist, gathering data to see if your fears hold true in the real world.\n");
    sb.append("        Exercise Start: 2025-09-06T17:00:00Z\n");
    sb.append("        Exercise End: 2025-10-31T17:00:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 7\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Generalization and Relapse Prevention\n");
    sb.append("    Counseling Plan phase duration in weeks: 2\n");
    sb.append("    Counseling Plan phase number: 3\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Build a Socially Confident Future\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will develop a plan to continue expanding their social comfort zone and will create a personal toolkit of strategies to manage any future re-emergence of social anxiety.\n");
    sb.append("\n\n"); // Separator

    // Plan 5: Obsessive-Compulsive Disorder (OCD)
    sb.append("--- Example Counseling Plan 5 (Obsessive-Compulsive Disorder) ---\n");
    sb.append("Counseling start: 2025-09-01T09:30:00Z\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Psychoeducation for OCD and ERP\n");
    sb.append("    Counseling Plan phase duration in weeks: 3\n");
    sb.append("    Counseling Plan phase number: 1\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Understand the OCD Cycle\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will understand that compulsions, while providing temporary relief, are the 'engine' that maintains the OCD cycle by preventing them from learning that their obsessional fears are unfounded.\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Develop an ERP Hierarchy\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will work with the therapist to create a detailed, stepped hierarchy of exposure tasks, rating each on a Subjective Units of Distress Scale (SUDS) from 0-100.\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Mapping Your OCD\n");
    sb.append(
        "        Exercise Description: For each of your main obsessions, create a map that details: 1. The obsession/intrusive thought. 2. The trigger(s). 3. The distress/fear. 4. The compulsion(s)/ritual(s) you perform. 5. The temporary relief.\n");
    sb.append(
        "        Exercise Explanation: This detailed mapping makes the invisible process of OCD visible and provides the specific targets we will address using Exposure and Response Prevention (ERP).\n");
    sb.append("        Exercise Start: 2025-09-01T10:30:00Z\n");
    sb.append("        Exercise End: 2025-09-22T10:30:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 2\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append(
        "    Counseling Plan phase name: Exposure and Response Prevention (ERP) Implementation\n");
    sb.append("    Counseling Plan phase duration in weeks: 10\n");
    sb.append("    Counseling Plan phase number: 2\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Confront Feared Triggers (Exposure)\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will systematically and repeatedly confront situations, objects, or thoughts from their hierarchy that trigger their obsessions.\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append(
        "        Counseling Plan Phase Goal name: Refrain from Compulsions (Response Prevention)\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will make a commitment to resist performing any compulsive rituals or mental acts before, during, and after exposure exercises, allowing the anxiety to decrease naturally (habituation).\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: In-Vivo ERP\n");
    sb.append(
        "        Exercise Description: Three times this week, perform the agreed-upon ERP exercise from your hierarchy (e.g., 'Touch the bathroom doorknob and then eat a snack without washing hands'). Remain in the situation and resist all compulsions until your SUDS rating reduces by at least 50%.\n");
    sb.append(
        "        Exercise Explanation: This is the core treatment for OCD. By facing your fear (Exposure) and not doing the ritual (Response Prevention), you teach your brain that you can handle the distress and that the feared outcome doesn't happen.\n");
    sb.append("        Exercise Start: 2025-09-23T10:30:00Z\n");
    sb.append("        Exercise End: 2025-12-01T10:30:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 2\n");
    sb.append("    --- Counseling Plan Exercise ---\n");
    sb.append("        Exercise Title: Imaginal Exposure\n");
    sb.append(
        "        Exercise Description: For obsessions without an obvious external trigger (e.g., fears of harming someone), you will write a detailed, first-person narrative about your feared scenario. You will read this script repeatedly for 30 minutes each day.\n");
    sb.append(
        "        Exercise Explanation: This allows us to apply ERP principles to purely mental obsessions, helping you habituate to the distressing thoughts themselves.\n");
    sb.append("        Exercise Start: 2025-10-07T10:30:00Z\n");
    sb.append("        Exercise End: 2025-12-01T10:30:00Z\n");
    sb.append("        Exercise is currently paused: false\n");
    sb.append("        Do every following days: 1\n");
    sb.append("--- Counseling Plan Phase ---\n");
    sb.append("    Counseling Plan phase name: Relapse Prevention and Mastery\n");
    sb.append("    Counseling Plan phase duration in weeks: 3\n");
    sb.append("    Counseling Plan phase number: 3\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append("        Counseling Plan Phase Goal name: Become Your Own OCD Therapist\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will learn to design their own ERP exercises for new or evolving OCD symptoms, demonstrating mastery of the principles.\n");
    sb.append("    --- Counseling Plan Phase Goal ---\n");
    sb.append(
        "        Counseling Plan Phase Goal name: Develop an OCD Relapse Prevention Blueprint\n");
    sb.append(
        "        Counseling Plan Phase Goal description: The patient will create a clear, actionable plan for identifying and responding to early signs of an OCD flare-up, ensuring long-term maintenance of gains.\n");

    return sb.toString();
  }
}
